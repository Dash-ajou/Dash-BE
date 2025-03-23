package io.saim.dash.coupon.issue.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.coupon.common.constant.CouponActiveStatus;
import io.saim.dash.coupon.common.constant.IssueStatus;
import io.saim.dash.coupon.issue.dto.IssueSignRequestDTO;
import io.saim.dash.coupon.issue.dto.IssueResultDTO;
import io.saim.dash.coupon.model.Coupon;
import io.saim.dash.coupon.model.DUMMY_GeneralUser;
import io.saim.dash.coupon.model.DUMMY_PartnerUser;
import io.saim.dash.coupon.model.DUMMY_ServiceUser;
import io.saim.dash.coupon.model.Issue;
import io.saim.dash.coupon.model.IssueLog;
import io.saim.dash.coupon.model.Product;
import io.saim.dash.coupon.model.QIssue;
import io.saim.dash.coupon.model.VendorGroup;
import io.saim.dash.coupon.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.repository.DUMMY.DUMMY_GeneralUserRepository;
import io.saim.dash.coupon.repository.DUMMY.DUMMY_PartnerUserRepository;
import io.saim.dash.coupon.repository.Issue.IssueRepository;

import io.saim.dash.coupon.repository.Log.IssueLog.IssueLogRepository;
import io.saim.dash.coupon.repository.Product.ProductRepository;
import io.saim.dash.coupon.repository.Vendor.VendorRepository;
import io.saim.dash.coupon.util.IssueQueryHelper;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;
	private final VendorRepository vendorRepository;
	private final ProductRepository productRepository;
	private final IssueLogRepository issueLogRepository;
	private final CouponRepository couponRepository;

	private final DUMMY_GeneralUserRepository generalUserRepository;
	private final DUMMY_PartnerUserRepository partnerUserRepository;

	public List<Issue> getIssuesByUser(
		DUMMY_ServiceUser user,
		int page, int size,
		String createat_start, String createat_end,
		String business_name, String owner_phone, IssueStatus status
	) {
		BooleanBuilder filterBuilder = IssueQueryHelper.createFilterBuilder(
			createat_start, createat_end,
			business_name, owner_phone, status,
			QIssue.issue
		);

		if(user.isPartner()) {
			assert user instanceof DUMMY_PartnerUser;
			return issueRepository.findIssuesByPartner((DUMMY_PartnerUser)user, filterBuilder, page, size);
		}

		assert user instanceof DUMMY_GeneralUser;
		return issueRepository.findIssuesByVendor((DUMMY_GeneralUser)user, filterBuilder, page, size);
	}

	public Issue getIssue(Long issueId, DUMMY_ServiceUser requestUser) throws ServiceException {
		Issue issue = issueRepository.getById(issueId)
			.orElseThrow(() -> new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND));

		if (requestUser.isPartner()) {
			if (!issue.isRequestedPartner(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		} else {
			if (!issue.getVendorGroup().isMemberIncluded(requestUser))
				throw new ServiceException(ServiceExceptionContent.ISSUE_FORBIDDEN);
		}

		return issue;
	}

	@Transactional(rollbackFor = Exception.class)
	public Issue createIssue(
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

		Issue issue = Issue.builder()
			.vendorGroup(issueVendor)
			.partner(partnerUser)
			.products(products)
			.build();

		issueRepository.save(issue);

		return issue;
	}

	@Transactional(rollbackFor = Exception.class)
	public IssueResultDTO signIssue(
		DUMMY_ServiceUser serviceUser, Long issueId,
		IssueStatus status,
		String paidAtString, List<IssueSignRequestDTO.IssuePaymentPriceInfo> price, Long discount
	) {
		if (!serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		if (
			(status == IssueStatus.APPROVED && (paidAtString == null || price.isEmpty())) ||
			(status == IssueStatus.REQUESTED)
		)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		Issue issue = getIssue(issueId, serviceUser);

		if (issue.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		Long paidPrice = getPaidPrice(price, discount);

		updateIssueStatus(issue, status);
		if (status == IssueStatus.DENIED)
			return new IssueResultDTO(issue, null);

		return issueCoupon(issue, LocalDateTime.parse(paidAtString), paidPrice);
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteIssue(
		DUMMY_ServiceUser serviceUser, Long issueId
	) {
		if (serviceUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(issueId, serviceUser);
		issueRepository.delete(issue);

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

	private IssueResultDTO issueCoupon(Issue issue, LocalDateTime paidAt, Long paidPrice) {
		List<Coupon> issuedCoupons = issue.getProducts().stream()
			.map(product -> Coupon.builder()
				.issueId(issue.getIssueId())
				.productId(product.getProductId())
				.registerCode(generateCouponRegisterCode(issue, 10))
				.build()
			)
			.toList();

		IssueLog issueLog = logCouponIssue(issue, paidAt, paidPrice);
		couponRepository.saveAll(issuedCoupons);

		return new IssueResultDTO(issue, issueLog);
	}

	private IssueLog logCouponIssue(Issue issue, LocalDateTime paidAt, Long paidPrice) {
		IssueLog issueLog = IssueLog.builder()
			.issueRequest(issue)
			.paidAt(paidAt)
			.paidPrice(paidPrice)
			.issueCnt(Integer.toUnsignedLong(issue.getProducts().size()))
			.couponActiveStatus(CouponActiveStatus.ENABLED)
			.build();
		issueLogRepository.save(issueLog);
		return issueLog;
	}

	private Long getPaidPrice(List<IssueSignRequestDTO.IssuePaymentPriceInfo> price, Long discount) {
		long paidPrice = price.stream()
			.map(IssueSignRequestDTO.IssuePaymentPriceInfo::getPrice)
			.reduce(Long::sum)
			.orElse(0L) - discount;

		if (paidPrice <= 0)
			throw new ServiceException(ServiceExceptionContent.BAD_ISSUE_SIGN_REQUEST);

		return paidPrice;
	}

	private void updateIssueStatus(Issue issue, IssueStatus status) {
		if (issue.getStatus() != IssueStatus.REQUESTED)
			throw new ServiceException(ServiceExceptionContent.ISSUE_ALREADY_SIGNED);

		issue.setStatus(status);
	}

	private static String generateCouponRegisterCode(Issue issueRequest, int length) {
		if (length < 10) length = 10;

		int basecode = issueRequest.hashCode();
		String prefix = Integer.toString(basecode).substring(0, 5);
		String uqn = Integer.toString((basecode + (int)(Math.random() % 1000) % 1000));

		return (prefix + uqn).substring(0, length);
	}
}
