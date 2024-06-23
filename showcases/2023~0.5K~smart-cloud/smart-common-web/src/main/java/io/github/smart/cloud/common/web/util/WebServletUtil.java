/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.smart.cloud.common.web.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.constants.SymbolConstant;
import io.github.smart.cloud.exception.ServerException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.stream.Stream;

/**
 * web相关工具类
 *
 * @author collin
 * @date 2019-04-03
 */
@UtilityClass
public class WebServletUtil {
	
	/**
	 * 获取请求的ip地址
	 *
	 * @return ip地址
	 */
	public static String getRealIp() {
		HttpServletRequest request = getHttpServletRequest();
		String ip = request.getHeader("x-forwarded-for");
		String unknown = "unknown";
		if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.contains(SymbolConstant.COMMA)) {
			ip = ip.substring(ip.lastIndexOf(SymbolConstant.COMMA) + 1, ip.length()).trim();
		}
		return ip;
	}

	/**
	 * 获取有效的请求参数（过滤掉不能序列化的）
	 * 
	 * @param args
	 * @return
	 */
	public static Object getRequestArgs(Object[] args) {
		if (ArrayUtils.isEmpty(args)) {
			return args;
		}

		boolean needFilter = false;
		for (Object arg : args) {
			if (needFilter(arg)) {
				needFilter = true;
				break;
			}
		}

		if (!needFilter) {
			return args.length == 1 ? args[0] : args;
		}

		Object[] tempArgs = Stream.of(args).filter(arg -> !needFilter(arg)).toArray();

		return getValidArgs(tempArgs);
	}

	/**
	 * 是否需要过滤
	 * 
	 * @param object
	 * @return
	 */
	private static boolean needFilter(Object object) {
		return !(object instanceof Serializable);
	}

	/**
	 * 获取有效的参数（如果是request对象，则优先从ParameterMap里取）
	 * 
	 * @param args
	 * @return
	 */
	private static Object getValidArgs(Object[] args) {
		if (ArrayUtils.isEmpty(args)) {
			return args;
		}

		if (args.length == 1 && args[0] instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) args[0];
			return request.getParameterMap();
		}

		return args;
	}

	/**
	 * 获取HttpServletRequest
	 * 
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(requestAttributes instanceof ServletRequestAttributes) {
			ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
			return attributes.getRequest();
		}
		
		throw new ServerException(CommonReturnCodes.GET_REQUEST_FAIL);
	}
	

	/**
	 * 获取HttpServletResponse
	 * 
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(requestAttributes instanceof ServletRequestAttributes) {
			ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
			return attributes.getResponse();
		}
		
		throw new ServerException(CommonReturnCodes.GET_RESPONSE_FAIL);
	}
	
}