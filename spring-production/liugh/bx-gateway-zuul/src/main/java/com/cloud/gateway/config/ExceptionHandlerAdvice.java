package com.cloud.gateway.config;

import javax.servlet.http.HttpServletResponse;

import com.cloud.utils.ResultHelper;
import com.netflix.client.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

	/**
	 * feignClient调用异常，将服务的异常和http状态码解析
	 * 
	 * @param exception
	 * @param response
	 * @return
	 */
	@ExceptionHandler({ FeignException.class })
	public ResultHelper feignException(FeignException exception, HttpServletResponse response) {
		int httpStatus = exception.status();
		if (httpStatus >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			log.error("feignClient调用异常", exception);
		}
		return ResultHelper.failed2Msg("服务端异常，feignClient调用异常");
	}

	@ExceptionHandler({ClientException.class, Throwable.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultHelper serverException(Throwable throwable) {
		log.error("服务端异常", throwable);
		return ResultHelper.failed2Msg("服务端异常，请联系管理员");
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badRequestException(IllegalArgumentException exception) {
		return ResultHelper.failed2Msg(exception.getMessage());
	}

	@ExceptionHandler({Exception.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultHelper badException(Exception exception) {
		return ResultHelper.failed2Msg(exception.getMessage());
	}




}
