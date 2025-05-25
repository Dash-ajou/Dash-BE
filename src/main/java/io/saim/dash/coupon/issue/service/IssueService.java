package io.saim.dash.coupon.issue.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.Request.RequestProductCountDTO;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.common.dto.Issue.IssueResultDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.repository.Request.RequestRepository;

import io.saim.dash.coupon.common.repository.Product.ProductRepository;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;
import io.saim.dash.coupon.common.util.IssueQueryHelper;
import io.saim.dash.coupon.common.dto.Request.RequestProductPriceDTO;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IssueService {

	private final RequestRepository requestRepository;
	private final IssueRepository issueRepository;
	private final VendorRepository vendorRepository;
	private final ProductRepository productRepository;

	private final PartnerUserRepository partnerUserRepository;
	private final GeneralUserRepository generalUserRepository;

	public List<Request> getRequestsByPartner(
		ServiceUser user,
		int page, int size,
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status
	) {
		BooleanBuilder filterBuilder = IssueQueryHelper.createFilterBuilder(
			createat_start, createat_end,
			business_name, owner_phone, status,
			QRequest.request
		);

		if(user.isPartner()) {
			assert user instanceof PartnerUser;
			return requestRepository.findRequestsByPartner((PartnerUser)user, filterBuilder, page, size);
		}

		assert user instanceof GeneralUser;
		return requestRepository.findRequestsByVendor((GeneralUser)user, filterBuilder, page, size);
	}

	public Request getRequest(Long requestId, ServiceUser requestUser) throws ServiceException {
		Request request = requestRepository.getById(requestId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));

		if (requestUser.isPartner()) {
			if (!request.isRequestedPartner(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		} else {
			System.out.println(request.getVendor().getVendorId());
			if (!request.getVendor().isMemberIncluded(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		}

		return request;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Request createRequest(
		ServiceUser serviceUser,
		String vendorName, String presidentName, String presidentPhone,
		String businessName, String ownerPhone,
		List<RequestProductCountDTO> productCounts
	) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		// temp: 기존 vendor 지정 없이 매 요청마다 신규 vendor 생성
		GeneralUser requestUser = (GeneralUser) serviceUser;
		Vendor issueVendor = createIssueVendor(
			requestUser,
			vendorName, presidentName, presidentPhone
		);

		PartnerUser partnerUser = getRequestPartner(businessName, ownerPhone);
		Request request = Request.builder()
			.vendor(issueVendor)
			.partner(partnerUser)
			.build();

		addProductsToRequest(partnerUser, productCounts, request);

		requestRepository.save(request);

		return request;
	}

	private void addProductsToRequest(PartnerUser partnerUser, List<RequestProductCountDTO> productCounts, Request request) {
		List<Product> products = getProducts(partnerUser, productCounts);
		Map<String, Long> requestProductCounts = getMappedProductCounts(productCounts, products);

		products.forEach(product -> request.addRequestProduct(
			product, requestProductCounts.get(product.getProductName())
		));
	}

	private static Map<String, Long> getMappedProductCounts(List<RequestProductCountDTO> productCounts, List<Product> referenceProducts) {
		Map<String, Long> requestProductCounts = productCounts.stream()
			.collect(Collectors.toMap(
				RequestProductCountDTO::getProductName, RequestProductCountDTO::getCount
			));
		return requestProductCounts;
	}

	private List<Product> getProducts(PartnerUser partnerUser, List<RequestProductCountDTO> productCounts) {
		List<Long> productIds = productCounts.stream()
			.map(RequestProductCountDTO::getProductId)
			.toList();

		// 이미 존재하는 productId 추출
		List<Product> products = productRepository.findAllById(productIds);
		Set<Long> existingIds = products.stream()
			.map(Product::getProductId)
			.collect(Collectors.toSet());

		// 존재하지 않는 ID에 대해 새 Product 생성
		List<Product> newProducts = productCounts.stream()
			.filter(dto -> !existingIds.contains(dto.getProductId()))
			.map(dto -> Product.builder()
				.partner(partnerUser)
				.productName(dto.getProductName()) // 임시 이름
				.price(0L)
				.build())
			.collect(Collectors.toList());

		// 기존 + 새 상품을 합침
		products.addAll(newProducts);

		return products;
	}

	@Transactional(rollbackFor = Exception.class)
	public IssueResultDTO signRequest(
		ServiceUser serviceUser, Long requestId,
		IssueStatus status,
		String paidAtString, List<RequestProductPriceDTO> price, Long discount
	) {
		if (!serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		if (
			(status == IssueStatus.APPROVED && (paidAtString == null || price.isEmpty())) ||
			(status == IssueStatus.REQUESTED)
		)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		Request request = getRequest(requestId, serviceUser);
		if (request.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		updateProductPriceInfo(request, price);
		Long paidPrice = getPaidPrice(price, discount);

		updateIssueRequestStatus(request, status);
		if (status == IssueStatus.DENIED)
			return new IssueResultDTO(request);

		return issueCoupon(request, LocalDateTime.parse(paidAtString), paidPrice);
	}

	private void updateProductPriceInfo(Request request, List<RequestProductPriceDTO> price) {
		if (request.getRequestProducts().isEmpty()) return;

		Map<Long, RequestProduct> mappedRequestProducts = request.getRequestProducts().stream()
			.collect(Collectors.toMap(
				rp -> rp.getProduct().getProductId(),
				rp -> rp
			));
		for (RequestProductPriceDTO priceDTO : price) {
			if (!mappedRequestProducts.containsKey(priceDTO.getProductId()))
				throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);
			mappedRequestProducts.get(priceDTO.getProductId()).setPrice(priceDTO.getPrice());
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteIssueRequest(
		ServiceUser loginUser, Long requestId
	) {
		if (loginUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		GeneralUser requestedUser = generalUserRepository.findById(((GeneralUser)loginUser).getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));

		Request request = getRequest(requestId, requestedUser);
		requestRepository.delete(request);

		return true;
	}

	private Vendor createIssueVendor(
		GeneralUser loginnedUser, String vendorName, String presidentName,
		String presidentPhone
	) {
		GeneralUser requestUser = generalUserRepository.findById(loginnedUser.getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));

		Vendor issueVendor = Vendor.builder()
			.name(vendorName)
			.presidentName(presidentName)
			.presidentPhone(presidentPhone)
			.build();

		vendorRepository.save(issueVendor);
		requestUser.addVendor(issueVendor);

		return issueVendor;
	}

	private PartnerUser getRequestPartner(
		String businessName, String ownerPhone
	) {
		PartnerUser partner = partnerUserRepository.findByPartnerName(businessName).orElse(null);
		if (partner == null) {
			partner = PartnerUser.builder()
				.partnerName(businessName)
				.phone(ownerPhone)
				.isTemporary(true)
				.temporaryRegisterDate(LocalDateTime.now())
				.build();

			partnerUserRepository.save(partner);
		}
		return partner;
	}

	private IssueResultDTO issueCoupon(Request request, LocalDateTime paidAt, Long paidPrice) {
		Issue issue = Issue.builder()
			.request(request)
			.paidAt(paidAt)
			.paidPrice(paidPrice)
			.issueCnt(Integer.toUnsignedLong(request.getRequestProducts().size()))
			.issueActiveStatus(IssueActiveStatus.ENABLED)
			.build();

		List<Coupon> issuedCoupons = createCoupons(issue);
		issueRepository.save(issue);
		return new IssueResultDTO(issue);
	}

	private static List<Coupon> createCoupons(Issue issue) {
		List<Coupon> createdCoupons = issue.getRequest().getRequestProducts().stream()
			.map(requestProduct -> Coupon.builder()
				.product(requestProduct.getProduct())
				.registrationCode(generateCouponRegisterCode(issue.getRequest(), 10))
				.price(requestProduct.getPrice())
				.expiredAt(LocalDateTime.now().plusMonths(1)) // temp: 모든 발행쿠폰 유효기간 1개월로 설정
				.issue(issue)
				.build()
			)
			.toList();
		return createdCoupons;
	}

	private Long getPaidPrice(List<RequestProductPriceDTO> price, Long discount) {
		long paidPrice = price.stream()
			.map(RequestProductPriceDTO::getPrice)
			.reduce(Long::sum)
			.orElse(0L) - discount;

		if (paidPrice <= 0)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		return paidPrice;
	}

	private void updateIssueRequestStatus(Request request, IssueStatus status) {
		if (request.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		request.setStatus(status);
	}

	private static String generateCouponRegisterCode(Request request, int length) {
		if (length < 10) length = 10;

		int basecode = request.hashCode();
		String prefix = Integer.toString(basecode).substring(0, 5);
		String uqn = Integer.toString((basecode + (int)(Math.random() % 1000) % 1000));

		return (prefix + uqn).substring(0, length);
	}
}
