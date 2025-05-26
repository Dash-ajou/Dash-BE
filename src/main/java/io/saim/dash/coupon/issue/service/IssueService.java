package io.saim.dash.coupon.issue.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.dto.Request.RequestPaymentInfo;
import io.saim.dash.coupon.common.dto.Request.RequestProductCountDTO;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.Request;
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
	private final NativeWebRequest nativeWebRequest;

	@Transactional
	public List<Request> getRequests(
		ServiceUser user,
		int page, int size,
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status
	) {
		BooleanBuilder filterBuilder = IssueQueryHelper.createFilterBuilder(
			createat_start, createat_end,
			business_name, owner_phone, status
		);

		if(user.isPartner()) {
			assert user instanceof PartnerUser;
			PartnerUser serviceUser = partnerUserRepository.findById(((PartnerUser)user).getId()).orElse(null);
			if (serviceUser == null) {
				throw new ServiceException(ServiceExceptionContent.USER_NOT_FOUND);
			}

			return requestRepository.findRequestsByPartner(serviceUser, filterBuilder, page, size);
		}

		assert user instanceof GeneralUser;
		GeneralUser serviceUser = generalUserRepository.findById(((GeneralUser)user).getId()).orElse(null);
		if (serviceUser == null) {
			throw new ServiceException(ServiceExceptionContent.USER_NOT_FOUND);
		}

		return requestRepository.findRequestsByVendor(serviceUser, filterBuilder, page, size);
	}

	public Request getRequest(Long requestId, ServiceUser requestUser) throws ServiceException {
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
		requestRepository.flush();

		return request;
	}

	private void addProductsToRequest(PartnerUser partnerUser, List<RequestProductCountDTO> productCounts, Request request) {
		List<Product> products = getProducts(partnerUser, productCounts);
		Map<String, Long> requestProductCounts = getMappedProductCounts(productCounts);

		products.forEach(product -> request.addRequestProduct(
			product, requestProductCounts.get(product.getProductName())
		));
	}

	private static Map<String, Long> getMappedProductCounts(List<RequestProductCountDTO> productCounts) {
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

	@Transactional
	public IssueResultDTO signRequest(
		ServiceUser loginUser, Long requestId,
		IssueStatus status,
		RequestPaymentInfo paymentInfo
	) {
		if (!loginUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		PartnerUser serviceUser = partnerUserRepository.findById(((PartnerUser)loginUser).getId())
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.USER_NOT_FOUND));
		System.out.println("[signRequest()]" + serviceUser.getUserType());
		serviceUser.setUserType(UserType.PARTNER); // TODO: 원인미상의 usertype drop

		System.out.println("[ID]" + serviceUser.getId());
		System.out.println("[EMAIL]" + serviceUser.getEmail());
		System.out.println("[TEMP]" + serviceUser.isTemporary());

		if (status == IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		Request request = getRequest(requestId, serviceUser);
		if (request.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		if (status == IssueStatus.DENIED) {
			updateIssueRequestStatus(request, status);
			return new IssueResultDTO(request);
		}

		if (paymentInfo == null)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		String paidAtString = paymentInfo.getPaidAt();
		List<RequestProductPriceDTO> price = paymentInfo.getPrices();
		Long discount = paymentInfo.getDiscount();

		updateProductPriceInfo(request, price);
		Long paidPrice = getPaidPrice(price, discount);

		updateIssueRequestStatus(request, status);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return issueCoupon(request, LocalDateTime.parse(paidAtString, formatter), paidPrice);
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
		PartnerUser partner = partnerUserRepository.findByPhone(ownerPhone).orElse(null);
		if (partner != null) return partner;

		System.out.println("===========++CREATE===============");

		partner = PartnerUser.builder()
			.partnerName(businessName)
			.phone(ownerPhone)
			.isTemporary(true)
			.temporaryRegisterDate(LocalDateTime.now())
			.build();
		partnerUserRepository.save(partner);

		System.out.println("===========++CREATED===============");

		System.out.println(partner.getId());

		System.out.println("===========++CREATED===============");

		return partner;
	}

	private IssueResultDTO issueCoupon(Request request, LocalDateTime paidAt, Long paidPrice) {
		Issue issue = Issue.builder()
			.request(request)
			.paidAt(paidAt)
			.paidPrice(paidPrice)
			.issueCnt(Integer.toUnsignedLong(request.getRequestProducts().size()))
			.usedCnt(0L)
			.issueActiveStatus(IssueActiveStatus.ENABLED)
			.build();

		List<Coupon> issuedCoupons = createCoupons(issue);
		issue.setIssueCnt(Integer.toUnsignedLong(issuedCoupons.size()));
		issue.addCoupons(issuedCoupons);
		issueRepository.save(issue);
		return new IssueResultDTO(issue);
	}

	private static List<Coupon> createCoupons(Issue issue) {
		Map<Long, Long> requestProductsCount = issue.getRequest().getRequestProducts().stream()
			.collect(Collectors.toMap(
				(val) -> val.getProduct().getProductId(), RequestProduct::getQuantity)
			);

		List<Coupon> createdCoupons = new ArrayList<>();
		issue.getRequest().getRequestProducts().forEach(requestProduct -> {
			Long count = requestProductsCount.get(requestProduct.getProduct().getProductId());
			for (int i = 0; i < count; i++) {
				createdCoupons.add(
					Coupon.builder()
					.product(requestProduct.getProduct())
					.registrationCode(generateCouponRegisterCode(issue.getRequest(), 10))
					.price(requestProduct.getPrice())
					.couponStatus(CouponStatus.REGISTERABLE)
					.expiredAt(LocalDateTime.now().plusMonths(1)) // temp: 모든 발행쿠폰 유효기간 1개월로 설정
					.issue(issue)
					.build()
				);
			}
		});
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
		StringBuilder codeBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int digit = (int)(Math.random() * 10);
			codeBuilder.append(digit);
		}
		return codeBuilder.toString();
	}
}
