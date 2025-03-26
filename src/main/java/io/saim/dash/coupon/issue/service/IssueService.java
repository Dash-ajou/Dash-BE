package io.saim.dash.coupon.issue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.common.model.IssueRequest;
import io.saim.dash.coupon.common.model.QIssueRequest;
import io.saim.dash.coupon.issue.dto.IRSignRequestDTO;
import io.saim.dash.coupon.common.dto.IssueResultDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.common.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.common.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.common.model.IssueLog;
import io.saim.dash.coupon.common.model.Product;
import io.saim.dash.coupon.common.model.VendorGroup;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_GeneralUserRepository;
import io.saim.dash.coupon.common.repository.DUMMY.DUMMY_PartnerUserRepository;
import io.saim.dash.coupon.common.repository.IssueRequest.IssueRequestRepository;

import io.saim.dash.coupon.common.repository.Log.IssueLog.IssueLogRepository;
import io.saim.dash.coupon.common.repository.Product.ProductRepository;
import io.saim.dash.coupon.common.repository.Vendor.VendorRepository;
import io.saim.dash.coupon.common.util.IssueQueryHelper;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IssueService {

	private final IssueRequestRepository issueRequestRepository;
	private final VendorRepository vendorRepository;
	private final ProductRepository productRepository;
	private final IssueLogRepository issueLogRepository;
	private final CouponRepository couponRepository;

	private final DUMMY_GeneralUserRepository generalUserRepository;
	private final DUMMY_PartnerUserRepository partnerUserRepository;

	public List<IssueRequest> getIssueRequestsByUser(
		DUMMY_ServiceUser user,
		int page, int size,
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status
	) {
		BooleanBuilder filterBuilder = IssueQueryHelper.createFilterBuilder(
			createat_start, createat_end,
			business_name, owner_phone, status,
			QIssueRequest.issueRequest
		);

		if(user.isPartner()) {
			assert user instanceof DUMMY_PartnerUser;
			return issueRequestRepository.findIssuesByPartner((DUMMY_PartnerUser)user, filterBuilder, page, size);
		}

		assert user instanceof DUMMY_GeneralUser;
		return issueRequestRepository.findIssuesByVendor((DUMMY_GeneralUser)user, filterBuilder, page, size);
	}

	public IssueRequest getIssueRequest(Long requestId, DUMMY_ServiceUser requestUser) throws ServiceException {
		IssueRequest issueRequest = issueRequestRepository.getById(requestId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));

		if (requestUser.isPartner()) {
			if (!issueRequest.isRequestedPartner(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		} else {
			if (!issueRequest.getVendorGroup().isMemberIncluded(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		}

		return issueRequest;
	}

	@Transactional(rollbackFor = Exception.class)
	public IssueRequest createIssueRequest(
		DUMMY_ServiceUser serviceUser,
		String vendorName, String presidentName, String presidentPhone,
		String businessName, String ownerPhone,
		List<Long> productIds
	) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		DUMMY_GeneralUser requestUser = (DUMMY_GeneralUser) serviceUser;
		VendorGroup issueVendor = createIssueVendor(
			requestUser,
			vendorName, presidentName, presidentPhone
		);

		DUMMY_PartnerUser partnerUser = getRequestPartner(businessName, ownerPhone);
		List<Product> products = productRepository.findAllById(productIds);

		IssueRequest issueRequest = IssueRequest.builder()
			.vendorGroup(issueVendor)
			.partner(partnerUser)
			.products(products)
			.build();

		issueRequestRepository.save(issueRequest);

		return issueRequest;
	}

	@Transactional(rollbackFor = Exception.class)
	public IssueResultDTO signIssueRequest(
		DUMMY_ServiceUser serviceUser, Long requestId,
		IssueStatus status,
		String paidAtString, List<IRSignRequestDTO.IssuePaymentPriceInfo> price, Long discount
	) {
		if (!serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		if (
			(status == IssueStatus.APPROVED && (paidAtString == null || price.isEmpty())) ||
			(status == IssueStatus.REQUESTED)
		)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		IssueRequest issueRequest = getIssueRequest(requestId, serviceUser);

		if (issueRequest.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		Long paidPrice = getPaidPrice(price, discount);

		updateIssueRequestStatus(issueRequest, status);
		if (status == IssueStatus.DENIED)
			return new IssueResultDTO(issueRequest);

		return issueCoupon(issueRequest, LocalDateTime.parse(paidAtString), paidPrice);
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteIssueRequest(
		DUMMY_ServiceUser serviceUser, Long requestId
	) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		IssueRequest issueRequest = getIssueRequest(requestId, serviceUser);
		issueRequestRepository.delete(issueRequest);

		return true;
	}

	private VendorGroup createIssueVendor(
		DUMMY_GeneralUser serviceUser, String vendorName, String presidentName,
		String presidentPhone
	) {
		VendorGroup issueVendor = VendorGroup.builder()
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

	private IssueResultDTO issueCoupon(IssueRequest issueRequest, LocalDateTime paidAt, Long paidPrice) {
		IssueLog issueLog = logCouponIssue(issueRequest, paidAt, paidPrice);
		List<Coupon> issuedCoupons = issueRequest.getProducts().stream()
			.map(product -> Coupon.builder()
				.issueLog(issueLog)
				.productId(product.getProductId())
				.registerCode(generateCouponRegisterCode(issueRequest, 10))
				.build()
			)
			.toList();

		couponRepository.saveAll(issuedCoupons);

		return new IssueResultDTO(issueLog, issuedCoupons);
	}

	private IssueLog logCouponIssue(IssueRequest issueRequest, LocalDateTime paidAt, Long paidPrice) {
		IssueLog issueLog = IssueLog.builder()
			.issueRequest(issueRequest)
			.paidAt(paidAt)
			.paidPrice(paidPrice)
			.issueCnt(Integer.toUnsignedLong(issueRequest.getProducts().size()))
			.couponActiveStatus(CouponActiveStatus.ENABLED)
			.build();
		issueLogRepository.save(issueLog);
		return issueLog;
	}

	private Long getPaidPrice(List<IRSignRequestDTO.IssuePaymentPriceInfo> price, Long discount) {
		long paidPrice = price.stream()
			.map(IRSignRequestDTO.IssuePaymentPriceInfo::getPrice)
			.reduce(Long::sum)
			.orElse(0L) - discount;

		if (paidPrice <= 0)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		return paidPrice;
	}

	private void updateIssueRequestStatus(IssueRequest issueRequest, IssueStatus status) {
		if (issueRequest.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		issueRequest.setStatus(status);
	}

	private static String generateCouponRegisterCode(IssueRequest issueRequest, int length) {
		if (length < 10) length = 10;

		int basecode = issueRequest.hashCode();
		String prefix = Integer.toString(basecode).substring(0, 5);
		String uqn = Integer.toString((basecode + (int)(Math.random() % 1000) % 1000));

		return (prefix + uqn).substring(0, length);
	}
}
