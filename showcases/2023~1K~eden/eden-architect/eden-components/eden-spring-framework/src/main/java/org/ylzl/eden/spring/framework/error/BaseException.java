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

package org.ylzl.eden.spring.framework.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.PropertyKey;

/**
 * 基础异常
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

	private String errCode;

	private String errMessage;

	private Object[] params;

	public BaseException(@PropertyKey(resourceBundle = ErrorCodeLoader.BUNDLE_NAME) String errCode, Throwable ex) {
		super(ErrorCodeLoader.getErrMessage(errCode, ex.getMessage()));
		this.errCode = errCode;
		this.errMessage = ErrorCodeLoader.getErrMessage(errCode, ex.getMessage());
	}

	public BaseException(@PropertyKey(resourceBundle = ErrorCodeLoader.BUNDLE_NAME) String errCode,
						 String errMessage, Object... params) {
		super(errMessage);
		this.errCode = errCode;
		this.errMessage = errMessage;
		this.params = params;
	}
}
