package io.saim.dash.coupon.manage.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.general.repository.GeneralUserRepository;
import io.saim.dash.account.partner.model.PartnerUser;
import io.saim.dash.account.partner.repository.PartnerUserRepository;
import io.saim.dash.account.push.model.Push;
import io.saim.dash.account.push.model.PushSenderType;
import io.saim.dash.account.push.model.PushTag;
import io.saim.dash.account.push.model.PushType;
import io.saim.dash.account.push.repository.PushRepository;
import io.saim.dash.coupon.common.constant.CouponExportType;
import io.saim.dash.coupon.common.constant.CouponStatus;
import io.saim.dash.coupon.common.constant.IssueActiveStatus;
import io.saim.dash.coupon.common.dto.Coupon.CouponBriefDTO;
import io.saim.dash.coupon.common.dto.Issue.CancelIssueResultDTO;
import io.saim.dash.coupon.common.dto.Issue.CouponIssueLogDTO;
import io.saim.dash.coupon.common.dto.Coupon.RegisteredCouponDTO;
import io.saim.dash.coupon.common.dto.Issue.PauseCouponsResultDTO;
import io.saim.dash.coupon.common.model.Coupon;
import io.saim.dash.coupon.common.model.CouponRegistration;
import io.saim.dash.coupon.common.model.Issue;
import io.saim.dash.coupon.common.model.QIssue;
import io.saim.dash.coupon.common.model.QRequest;
import io.saim.dash.coupon.common.model.Request;
import io.saim.dash.coupon.common.repository.Coupon.CouponRegistrationRepository;
import io.saim.dash.coupon.common.repository.Coupon.CouponRepository;
import io.saim.dash.coupon.common.repository.Issue.IssueRepository;
import io.saim.dash.coupon.common.util.ManageQueryHelper;
import io.saim.dash.coupon.issue.service.ExternalRequestService;
import io.saim.dash.coupon.manage.dto.ExportResponseDTO;
import io.saim.dash.global.exception.ServiceException;
import io.saim.dash.global.exception.ServiceExceptionContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

	private final IssueRepository issueRepository;
	private final CouponRepository couponRepository;
	private final CouponRegistrationRepository couponRegistrationRepository;
	private final PartnerUserRepository partnerUserRepository;
	private final GeneralUserRepository generalUserRepository;
	private final PushRepository pushRepository;
	private final ExternalRequestService externalRequestService;

	private final Region AWS_REGION = Region.AP_NORTHEAST_2;
	private final String AWS_EXPORT_BUCKET = "dash-form-bucket";
	private final String AWS_CSV_UPLOAD_DIR = "csv/";

	public List<CouponIssueLogDTO> getIssuedRequests(
		ServiceUser user,
		Integer page, Integer size,
		String vendorName, String presidentName, String businessName, Boolean isCompletionInclude
	) {
		BooleanBuilder filterBuilder = ManageQueryHelper.createIssueSearchFilterBuilder(
			vendorName, presidentName, businessName, isCompletionInclude,
			QIssue.issue, QRequest.request
		);

		List<Issue> issues;
		if (user.isPartner()) {
			PartnerUser partnerUser = partnerUserRepository.getById(((PartnerUser)user).getId());

			issues = issueRepository.findIssuesByPartner(
				partnerUser, filterBuilder,
				page, size
			);
		} else {
			GeneralUser generalUser = generalUserRepository.getById(((GeneralUser)user).getId());
			issues = issueRepository.findIssuesByVendor(
				generalUser, filterBuilder,
				page, size
			);
		}

		return issues.stream()
			.map(CouponIssueLogDTO::new)
			.toList();

	}

	public List<CouponBriefDTO> getCouponsByIssueId(
		ServiceUser loginUser,
		Integer page, Integer size,
		Long issueId
	) {
		ServiceUser user;
		if (loginUser.isPartner()) {
			user = partnerUserRepository.getById(((PartnerUser)loginUser).getId());
		} else {
			user = generalUserRepository.getById(((GeneralUser)loginUser).getId());
		}
		user.setUserType(loginUser.getUserType());

		getIssue(user, issueId);
		List<Coupon> savedCoupons = couponRepository.findCouponsByIssueId(
			issueId
		);

		return savedCoupons.stream()
			.map(CouponBriefDTO::new)
			.toList();
	}

	public RegisteredCouponDTO getCouponByCouponId(
		ServiceUser loginUser,
		Long couponId
	) {
		ServiceUser user = getLoginedUserInfo(loginUser);
		Coupon coupon = getCouponById(couponId, user);

		Issue issue = coupon.getIssue();
		CouponRegistration registration = couponRegistrationRepository.findByCoupon(coupon);
		return new RegisteredCouponDTO(issue, coupon, registration);
	}

	private Coupon getCouponById(Long couponId, ServiceUser user) {
		Coupon coupon = couponRepository.findCouponById(couponId);

		if (user.isPartner() && coupon.getIssue().getPartner() != user)
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);
		if (!user.isPartner() && !coupon.getIssue().getVendor().isMemberIncluded(user))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return coupon;
	}

	private ServiceUser getLoginedUserInfo(ServiceUser loginUser) {
		if (loginUser.isPartner())
			return partnerUserRepository.getReferenceById(((PartnerUser)loginUser).getId());
		else
			return generalUserRepository.getReferenceById(((GeneralUser)loginUser).getId());
	}

	@Transactional
	public PauseCouponsResultDTO updateCouponsPauseStatus(
		ServiceUser loginUser, Long issueId, IssueActiveStatus status
	) {
		GeneralUser user = generalUserRepository.getById(((GeneralUser)loginUser).getId());

		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(user, issueId);

		IssueActiveStatus currentStatus = issue.getIssueActiveStatus();
		if (currentStatus == status)
			throw new ServiceException(ServiceExceptionContent.ACTIVE_STATUS_ALREADY_UPDATED);
		if (status == IssueActiveStatus.CANCELLED)
			throw new ServiceException(ServiceExceptionContent.INVALID_INPUT);

		Long updatedCounts = issue.getIssueCnt() - issue.getUsedCnt();
		issue.setIssueActiveStatus(status);
		return new PauseCouponsResultDTO(issue.getIssueCnt(), updatedCounts);
	}

	private Issue getIssue(ServiceUser user, Long issueId) {
		Issue issue = issueRepository.getById(issueId);
		if (issue == null)
			throw new ServiceException(ServiceExceptionContent.ISSUE_NOT_FOUND);
		if (!user.isPartner() && !issue.getRequest().getVendor().isMemberIncluded(user))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		if (user.isPartner() && !issue.getRequest().getPartner().equals(user))
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		return issue;
	}

	@Transactional
	public CouponRegistration cancelCouponRegistration(
		ServiceUser loginUser,
		Long issueId, Long couponId
	) {
		if (loginUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		GeneralUser user = generalUserRepository.getById(((GeneralUser)loginUser).getId());

		getIssue(user, issueId);
		CouponRegistration registration = couponRegistrationRepository.findByCouponId(couponId);
		registration.setIsValid(false);

		return registration;
	}

	public Issue getRequestedCancelIssue(ServiceUser loginUser, Long issueId) {
		GeneralUser user = generalUserRepository.getById(((GeneralUser)loginUser).getId());

		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		Issue issue = getIssue(user, issueId);
		if (issue.getIssueActiveStatus() != IssueActiveStatus.DISABLE)
			throw new ServiceException(ServiceExceptionContent.ISSUE_NOT_DISABLED);

		return issue;
	}

	@Transactional
	public CancelIssueResultDTO cancelIssue(ServiceUser loginUser, Issue issue, Boolean verifyResult) {
		GeneralUser user = generalUserRepository.getById(((GeneralUser)loginUser).getId());
		if (user.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		if (!verifyResult)
			throw new ServiceException(ServiceExceptionContent.INVALID_VERIFICATION_NUMBER);

		BooleanBuilder couponFilterBuilder = ManageQueryHelper.createCouponSearchFilterBuilder(
			List.of(new CouponStatus[]{CouponStatus.DISABLED}),
			issue.getIssueId()
		);
		Long expiredCnt = couponRepository.cancelCoupons(couponFilterBuilder);
		issue.setIssueActiveStatus(IssueActiveStatus.CANCELLED);

		sendIssueCancelledPush(issue);

		return new CancelIssueResultDTO(issue, expiredCnt);
	}

	private void sendIssueCancelledPush(Issue issue) {
		List<Push> pushes = new java.util.ArrayList<>(issue.getVendor().getVendorUsers().stream()
			.map(vendorUser -> createSystemPushMessage(
				PushTag.ISSUE_CANCELLED,
				vendorUser,
				issue.getPartner().getPartnerName()
			))
			.toList());
		pushes.add(createSystemPushMessage(
			PushTag.ISSUE_CANCELLED,
			issue.getPartner(),
			issue.getVendor().getName()
		));
		pushRepository.saveAll(pushes);
	}

	private static Push createSystemPushMessage(PushTag tag, ServiceUser receiver, String message) {
		Push.PushBuilder pushBuilder = Push.builder()
			.type(PushType.INFO)
			.tag(tag)
			.message(message)
			.senderType(PushSenderType.SYSTEM)
			.receivedAt(LocalDateTime.now());

		if (receiver.isPartner()) pushBuilder.receiver_partner((PartnerUser)receiver);
		else pushBuilder.receiver_general((GeneralUser)receiver);

		return pushBuilder.build();
	}

	@Transactional
	public ExportResponseDTO exportCouponList(ServiceUser loginUser, CouponExportType couponExportType, Long issueId) {
		if (loginUser.isPartner())
			throw new ServiceException(ServiceExceptionContent.NO_PERMISSION);

		GeneralUser user = generalUserRepository.getById(((GeneralUser)loginUser).getId());
		Issue issue = getIssue(user, issueId);

		if (couponExportType == CouponExportType.image) return exportImageCouponList(issue);
		return exportCSVCouponList(issue);

	}

	private ExportResponseDTO exportImageCouponList(Issue issue) {
		String couponImageKey = issue.getCouponImageKey();
		if (couponImageKey == null) {
			triggerCouponImageCreate(issue.getRequest());
			throw new ServiceException(ServiceExceptionContent.IMAGE_NOT_READY);
		}

		System.out.println("[COUPON_IMAGE_KEY] " + couponImageKey);

		try {
			return ExportResponseDTO.builder()
				.requestId(issue.getRequest().getRequestId())
				.exportType(CouponExportType.image)
				.downloadUrl(generatePresignedUrl(couponImageKey))
				.build();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(ServiceExceptionContent.FILE_GET_ERROR);
		}
	}

	private void triggerCouponImageCreate(Request request) {
		try {
			Issue issue = issueRepository.getByRequest(request);
			if (issue == null || request.getCouponForm() == null)
				throw new Exception();

			externalRequestService.requestCouponImageCreate(request.getRequestId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@NotNull
	private ExportResponseDTO exportCSVCouponList(Issue issue) {
		String couponCsvKey = createCSVCouponList(issue);
		System.out.println("[COUPON_CSV_KEY] " + couponCsvKey);

		try {
			return ExportResponseDTO.builder()
				.requestId(issue.getRequest().getRequestId())
				.exportType(CouponExportType.csv)
				.downloadUrl(generatePresignedUrl(couponCsvKey))
				.build();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(ServiceExceptionContent.FILE_GET_ERROR);
		}
	}

	private String createCSVCouponList(Issue issue) {
		String filename = getCSVFileName(issue);
		try {
			String csvContents = convertToCsv(issue.getIssueId());
			File csvTempFile = writeCsvToFile(csvContents);
			uploadToS3(csvTempFile, filename);
			issue.setCouponCsvKey(filename);
			return filename;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServiceException(ServiceExceptionContent.FILE_SAVE_ERROR);
		}
	}

	private String getCSVFileName(Issue issue) {
		long time_ms = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return AWS_CSV_UPLOAD_DIR + time_ms + "_coupon_list" + ".csv";
	}

	private String convertToCsv(Long issueId) {
		List<CouponRegistration> registeredCouponInfo = couponRepository.findRegistrationsByIssueIdOnCoupon(issueId);

		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		sb.append("연번,쿠폰상태,발행일,만료일,교환품명,교환품 정가,수령자 이름,수령자 연락처\n");
		int cnt = 0;
		for (CouponRegistration cr : registeredCouponInfo) {
			Coupon coupon = cr.getCoupon();
			String csvColumn = String.format("%d,%s,%s,%s,%s,%d",
				cnt++,
				coupon.getCouponStatus().getStringValue(),
				coupon.getCreatedAt().format(formatter),
				coupon.getExpiredAt().format(formatter),
				coupon.getProduct().getProductName(),
				coupon.getPrice()
			);
			if (cr.getIsValid()) {
				csvColumn += String.format(",%s,%s\n",
					cr.getRegisteredUser().getOwnerName(),
					cr.getRegisteredUser().getOwnerPhone()
				);
			} else csvColumn += ",,\n";

			sb.append(csvColumn);
		}
		return sb.toString();
	}

	private File writeCsvToFile(String csvContent) throws IOException {
		File tempFile = File.createTempFile("coupons-", ".csv");
		try (FileWriter writer = new FileWriter(tempFile)) {
			writer.write(csvContent);
		}
		return tempFile;
	}

	private String generatePresignedUrl(String key) {
		S3Presigner presigner = S3Presigner.create();
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
			.bucket(AWS_EXPORT_BUCKET)
			.key(key)
			.build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMinutes(15)) // 예: 15분간 유효
			.getObjectRequest(getObjectRequest)
			.build();

		PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
		return presignedRequest.url().toString();
	}

	private void uploadToS3(File file, String key) {
		S3Client s3 = S3Client.builder().region(AWS_REGION).build();
		PutObjectRequest request = PutObjectRequest.builder()
			.bucket(AWS_EXPORT_BUCKET)
			.key(key)
			.build();
		s3.putObject(request, RequestBody.fromFile(file));
	}
}
