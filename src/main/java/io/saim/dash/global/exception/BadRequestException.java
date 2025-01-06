package io.saim.dash.global.exception;

public class BadRequestException extends RuntimeException {

	public BadRequestException() { super("올바르지 않은 요청입니다"); }

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String key, String value) { super( String.format("올바르지 않은 %s 요청입니다: %s", key, value) ); }
}
