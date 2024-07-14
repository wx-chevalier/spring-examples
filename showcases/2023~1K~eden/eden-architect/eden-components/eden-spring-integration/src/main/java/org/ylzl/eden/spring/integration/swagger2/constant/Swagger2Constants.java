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

package org.ylzl.eden.spring.integration.swagger2.constant;

import lombok.experimental.UtilityClass;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger2.web.Swagger2Controller;

/**
 * Swagger2 常量定义
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public final class Swagger2Constants {

	public static final String DEFAULT_URL = Swagger2Controller.DEFAULT_URL;

	public static final String RESOURCES_URL = "/swagger-resources";

	public static final String RESOURCES_CONF_URL = RESOURCES_URL + "/configuration";

	public static final String VERSION = DocumentationType.SWAGGER_2.getVersion();
}
