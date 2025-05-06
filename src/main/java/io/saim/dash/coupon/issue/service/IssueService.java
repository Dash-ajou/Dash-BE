package io.saim.dash.coupon.issue.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.Request.RequestProductCountDTO;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.Vendor;
import io.saim.dash.coupon.common.model.mapping.RequestProduct;
import io.saim.dash.coupon.common.repository.jpa.IssueJpaRepository;
import io.saim.dash.coupon.common.dto.Issue.IssueResultDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_PartnerUserRepository;
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
	private final IssueJpaRepository issueRepository;
	private final VendorRepository vendorRepository;
	private final ProductRepository productRepository;

	private final DUMMY_PartnerUserRepository partnerUserRepository;

	public List<Request> getIssueRequestsByUser(
		DUMMY_ServiceUser user,
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
			assert user instanceof DUMMY_PartnerUser;
			return requestRepository.findIssuesByPartner((DUMMY_PartnerUser)user, filterBuilder, page, size);
		}

		assert user instanceof DUMMY_GeneralUser;
		return requestRepository.findIssuesByVendor((DUMMY_GeneralUser)user, filterBuilder, page, size);
	}

	public Request getRequest(Long requestId, DUMMY_ServiceUser requestUser) throws ServiceException {
		Request request = requestRepository.getById(requestId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));

		if (requestUser.isPartner()) {
			if (!request.isRequestedPartner(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		} else {
			if (!request.getVendor().isMemberIncluded(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		}

		return request;
	}

	@Transactional(rollbackFor = Exception.class)
	public Request createIssueRequest(
		DUMMY_ServiceUser serviceUser,
		String vendorName, String presidentName, String presidentPhone,
		String businessName, String ownerPhone,
		List<RequestProductCountDTO> productCounts
	) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		// temp: 기존 vendor 지정 없이 매 요청마다 신규 vendor 생성
		DUMMY_GeneralUser requestUser = (DUMMY_GeneralUser) serviceUser;
		Vendor issueVendor = createIssueVendor(
			requestUser,
			vendorName, presidentName, presidentPhone
		);

		DUMMY_PartnerUser partnerUser = getRequestPartner(businessName, ownerPhone);
		Request request = Request.builder()
			.vendor(issueVendor)
			.partner(partnerUser)
			.build();

		addProductsToRequest(productCounts, request);

		requestRepository.save(request);

		return request;
	}

	private void addProductsToRequest(List<RequestProductCountDTO> productCounts, Request request) {
		List<Product> products = getProducts(productCounts);
		Map<Long, Long> requestProductCounts = getMappedProductCounts(productCounts);

		products.forEach(product -> request.addRequestProduct(
			product, requestProductCounts.get(product.getProductId())
		));
	}

	private static Map<Long, Long> getMappedProductCounts(List<RequestProductCountDTO> productCounts) {
		Map<Long, Long> requestProductCounts = productCounts.stream()
			.collect(Collectors.toMap(
				RequestProductCountDTO::getProductId, RequestProductCountDTO::getCount
			));
		return requestProductCounts;
	}

	private List<Product> getProducts(List<RequestProductCountDTO> productCounts) {
		List<Long> productIds = productCounts.stream()
			.map(RequestProductCountDTO::getProductId)
			.toList();
		List<Product> products = productRepository.findAllById(productIds);
		return products;
	}

	@Transactional(rollbackFor = Exception.class)
	public IssueResultDTO signRequest(
		DUMMY_ServiceUser serviceUser, Long requestId,
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
		DUMMY_ServiceUser serviceUser, Long requestId
	) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Request request = getRequest(requestId, serviceUser);
		requestRepository.delete(request);

		return true;
	}

	private Vendor createIssueVendor(
		DUMMY_GeneralUser serviceUser, String vendorName, String presidentName,
		String presidentPhone
	) {
		Vendor issueVendor = Vendor.builder()
			.name(vendorName)
			.presidentName(presidentName)
			.presidentPhone(presidentPhone)
			.build();

		vendorRepository.save(issueVendor);
		serviceUser.addVendor(issueVendor);

		return issueVendor;
	}

	private DUMMY_PartnerUser getRequestPartner(
		String businessName, String ownerPhone
	) {
		DUMMY_PartnerUser partner = partnerUserRepository.findPartnerByBusinessName(businessName);
		if (partner == null) {
			partner = DUMMY_PartnerUser.builder()
				.name(businessName)
				.phone(ownerPhone)
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
			.couponActiveStatus(CouponActiveStatus.ENABLED)
			.build();

		List<Coupon> issuedCoupons = createCoupons(issue);
		issueRepository.save(issue);
		return new IssueResultDTO(issue);
	}

	private static List<Coupon> createCoupons(Issue issue) {
		List<Coupon> createdCoupons = issue.getRequest().getRequestProducts().stream()
			.map(requestProduct -> Coupon.builder()
				.product(requestProduct.getProduct())
				.registerCode(generateCouponRegisterCode(issue.getRequest(), 10))
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
