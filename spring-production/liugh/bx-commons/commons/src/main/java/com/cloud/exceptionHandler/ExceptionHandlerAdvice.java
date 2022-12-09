package com.cloud.exceptionHandler;

import com.cloud.utils.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badRequestException(IllegalArgumentException exception) {
		return ResultHelper.failed2Msg(exception.getMessage());
	}

	@ExceptionHandler({GFCException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badGFCException(GFCException exception) {
		return ResultHelper.failed2Msg(exception.getErrMsg());
	}

	@ExceptionHandler({Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badException(Exception exception) {
		return ResultHelper.failed2Msg(exception.getMessage());
	}
}
