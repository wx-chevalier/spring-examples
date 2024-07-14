/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ylzl.eden.commons.env.browser;

import com.google.common.net.HttpHeaders;
import lombok.Getter;
import lombok.NonNull;
import org.ylzl.eden.commons.regex.RegexUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 浏览器枚举
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Getter
public enum Browser {

	CHROME("Chrome"),
	FIREFOX("Firefox"),
	OPERA("Opera"),
	SAFARI("Safari"),
	IE6("MSIE 6.0"),
	IE7("MSIE 7.0"),
	IE8("MSIE 8.0"),
	IE9("MSIE 9.0"),
	IE10("MSIE 10.0"),
	IE11("rv:11.0"),

	GREEN("GreenBrowser"),
	MAXTHON("Maxthon"),
	QQ("QQBrowser"),
	SE360("360SE"),
	OTHER("其它");

	private final String userAgent;

	Browser(String userAgent) {
		this.userAgent = userAgent;
	}

	public static Browser parse(@NonNull HttpServletRequest request) {
		final String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
		for (Browser browser : Browser.values()) {
			if (RegexUtils.find(browser.getUserAgent(), userAgent)) {
				return browser;
			}
		}
		return null;
	}
}
