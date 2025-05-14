package io.saim.dash.global.exception;

import io.saim.dash.global.dto.APIStatus;
import lombok.Getter;

public class ServiceException extends RuntimeException {

	@Getter
	private final APIStatus apiStatus;

	public ServiceException() {
		super();
		this.apiStatus = APIStatus.FAILURE;
	}

	public ServiceException(ServiceExceptionContent content) {
		super(content.getMessage());
		this.apiStatus = content.getApiStatus();
	}
}
