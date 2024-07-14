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
package org.ylzl.eden.spring.boot.profile.env;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.ylzl.eden.spring.framework.bootstrap.constant.SpringProfiles;

/**
 * Profile 配置属性
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Getter
@Setter
@ConfigurationProperties(prefix = ProfileProperties.PREFIX)
public class ProfileProperties {

	public static final String PREFIX = "spring.profile";

	private String[] displayOnActiveProfiles = {
		SpringProfiles.SPRING_PROFILE_DEVELOPMENT,
		SpringProfiles.SPRING_PROFILE_TEST,
		SpringProfiles.SPRING_PROFILE_DEMO
	};
}
