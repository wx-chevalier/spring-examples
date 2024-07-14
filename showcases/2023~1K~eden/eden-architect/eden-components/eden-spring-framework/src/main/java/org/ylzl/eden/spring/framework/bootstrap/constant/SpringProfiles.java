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

package org.ylzl.eden.spring.framework.bootstrap.constant;

import lombok.experimental.UtilityClass;

/**
 * Profile 常量定义
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public final class SpringProfiles {

	/**
	 * 本地环境
	 */
	public static final String SPRING_PROFILE_LOCALHOST = "local";

	/**
	 * 开发环境
	 */
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

	/**
	 * 生产环境
	 */
	public static final String SPRING_PROFILE_PRODUCTION = "prod";

	/**
	 * 预发布环境
	 */
	public static final String SPRING_PROFILE_STAGING = "staging";

	/**
	 * 测试环境
	 */
	public static final String SPRING_PROFILE_TEST = "test";

	/**
	 * 演示环境
	 */
	public static final String SPRING_PROFILE_DEMO = "demo";
}
