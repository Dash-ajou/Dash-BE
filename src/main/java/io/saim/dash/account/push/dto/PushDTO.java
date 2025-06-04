package io.saim.dash.account.push.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.saim.dash.account.push.model.Push;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PushDTO {
	private Long notification_id;
	private String type;
	private String message;
	private String sender_type;
	private Long user_id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime received_at;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime read_at;

	private Boolean readed;

	public PushDTO(Push push) {
		notification_id = push.getId();
		type = push.getType().toString();
		message = push.getMessage();
		sender_type = push.getSenderType().toString();
		user_id = push.getReceiver().getId();
		received_at = push.getReceivedAt();
		read_at = push.getReadAt();
		readed = push.isReaded();

	}
}
