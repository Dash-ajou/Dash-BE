package io.saim.dash.account.push.model;

import java.time.LocalDateTime;

import io.saim.dash.account.common.model.ServiceUser;
import io.saim.dash.account.common.model.UserType;
import io.saim.dash.account.general.model.GeneralUser;
import io.saim.dash.account.partner.model.PartnerUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "push")
public class Push {

	@Id @Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "push_id")
	private Long id;

	@Getter
	@Enumerated(EnumType.STRING)
	private PushType type;

	@Getter
	@Enumerated(EnumType.STRING)
	private PushTag tag;

	@Getter
	private String message;

	@Getter
	@Enumerated(EnumType.STRING)
	private PushSenderType senderType;

	@ManyToOne
	@JoinColumn(name = "receiver_general_id")
	private GeneralUser receiver_general;

	@ManyToOne
	@JoinColumn(name = "receiver_partner_id")
	private PartnerUser receiver_partner;

	@Getter
	private LocalDateTime receivedAt;

	@Getter @Setter
	private LocalDateTime readAt;

	public Push(
		PushSenderType senderType,
		GeneralUser receiver_general, PartnerUser receiver_partner,
		LocalDateTime receivedAt, PushTag tag, PushType type
	) {
		this(
			senderType, tag.getMessage(),
			receiver_general, receiver_partner,
			receivedAt, tag, type
		);
	}

	public Push(
		PushSenderType senderType, String message,
		GeneralUser receiver_general, PartnerUser receiver_partner,
		LocalDateTime receivedAt, PushTag tag, PushType type
	) {
		this.type = type;
		this.tag = tag;
		this.message = message;

		this.senderType = senderType;
		this.receiver_general = receiver_general;
		this.receiver_partner = receiver_partner;
		this.receivedAt = receivedAt;
	}

	public ServiceUser getReceiver() {
		if (receiver_general == null) return receiver_partner;
		return receiver_general;
	}

	public Boolean isReaded() {
		return readAt != null;
	}
}
