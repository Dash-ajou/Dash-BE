package io.saim.dash.global.exception;

import lombok.Getter;

public class ServiceException extends RuntimeException {

	@Getter
	private final Integer statusCode;

	public ServiceException() {
		super();
		this.statusCode = 500;
	}

	public ServiceException(ServiceExceptionContent content) {
		super(content.getMessage());
		this.statusCode = content.getStatusCode();
	}
}
