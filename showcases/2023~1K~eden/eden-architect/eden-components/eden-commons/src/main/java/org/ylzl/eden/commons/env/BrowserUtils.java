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
package org.ylzl.eden.commons.env;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.ylzl.eden.commons.env.browser.Browser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 浏览器工具集
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class BrowserUtils {

	public static boolean isIE(@NonNull HttpServletRequest request) {
		Browser browser = Browser.parse(request);
		switch (Objects.requireNonNull(browser)) {
			case IE6:
			case IE7:
			case IE8:
			case IE9:
			case IE10:
			case IE11:
				return true;
		}
		return false;
	}

	public static String getLanguage(@NonNull HttpServletRequest request) {
		return request.getLocale().getLanguage();
	}

	public static String resolveValue(@NonNull String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, String.valueOf(Charsets.UTF_8));
	}
}
