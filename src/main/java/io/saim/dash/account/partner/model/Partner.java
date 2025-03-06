package io.saim.dash.account.partner.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "partner")
public class Partner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long partnerId;

	@Column(nullable = false)
	private String partnerName;

	@Column(nullable = false)
	private String partnerAddress;

	@Column(nullable = false)
	private String ownerName;

	@Column(nullable = false)
	private String ownerPhone;

	@Column(nullable = false, unique = true)
	private String ownerEmail;

	@Column(nullable = false)
	private boolean isTemporary;

	@Column(nullable = true)
	private LocalDateTime temporaryRegisterDate;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
