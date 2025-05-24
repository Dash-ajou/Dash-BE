package io.saim.dash.global.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceExceptionContentTest {

	@DisplayName("예외유형의 메세지 형식에 따라 오류내용을 표현할 수 있다")
	@Test
	void replaceArg() {
		ServiceException serviceException = new ServiceException(
			ServiceExceptionContent.TEST_METHOD_REQUESTED
				.replaceArg("abc")
		);

		assertEquals(
			serviceException.getMessage(),
			"사용할 수 없는 요청입니다: abc"
		);
	}
}
