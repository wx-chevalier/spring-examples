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

package org.ylzl.eden.spring.framework.web.extension;

import org.jetbrains.annotations.PropertyKey;
import org.ylzl.eden.commons.lang.MessageFormatUtils;
import org.ylzl.eden.spring.framework.error.ErrorCodeLoader;

/**
 * 默认响应体构建
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.x
 */
public class InternalResponseBuilder implements ResponseBuilder<InternalResponse<?>> {

	@Override
	public InternalResponse<?> buildSuccess() {
		InternalResponse<?> internalResponse = new InternalResponse<>();
		internalResponse.setSuccess(true);
		return internalResponse;
	}

	@Override
	public <Body> InternalResponse<?> buildSuccess(Body data) {
		InternalResponse<Body> internalResponse = new InternalResponse<>();
		internalResponse.setSuccess(true);
		internalResponse.setData(data);
		return internalResponse;
	}

	@Override
	public InternalResponse<?> buildFailure(@PropertyKey(resourceBundle = ErrorCodeLoader.BUNDLE_NAME) String errCode,
											Object... params) {
		InternalResponse<?> internalResponse = new InternalResponse<>();
		internalResponse.setSuccess(false);
		internalResponse.setCode(errCode);
		internalResponse.setMessage(ErrorCodeLoader.getErrMessage(errCode, params));
		return internalResponse;
	}

	@Override
	public InternalResponse<?> buildFailure(@PropertyKey(resourceBundle = ErrorCodeLoader.BUNDLE_NAME) String errCode,
											String errMessage, Object... params) {
		InternalResponse<?> internalResponse = new InternalResponse<>();
		internalResponse.setSuccess(false);
		internalResponse.setCode(errCode);
		internalResponse.setMessage(MessageFormatUtils.format(errMessage, params));
		return internalResponse;
	}
}
