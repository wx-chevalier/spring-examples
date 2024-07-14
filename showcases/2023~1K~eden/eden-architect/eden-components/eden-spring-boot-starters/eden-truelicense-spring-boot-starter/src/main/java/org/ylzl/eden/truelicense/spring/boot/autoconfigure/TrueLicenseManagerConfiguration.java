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

package org.ylzl.eden.truelicense.spring.boot.autoconfigure;

import de.schlichtherle.license.LicenseManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.ylzl.eden.truelicense.spring.boot.env.TrueLicenseProperties;
import org.ylzl.eden.spring.integration.truelicense.config.TrueLicenseConfig;
import org.ylzl.eden.spring.integration.truelicense.manager.CustomLicenseManager;

/**
 * TrueLicense 配置
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@EnableConfigurationProperties(TrueLicenseProperties.class)
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Configuration(proxyBeanMethods = false)
public class TrueLicenseManagerConfiguration {

	private static final String MSG_AUTOWIRED_TRUE_LICENSE_MANAGER = "Autowired TureLicense manager";

	private final TrueLicenseProperties trueLicenseProperties;

	public TrueLicenseManagerConfiguration(TrueLicenseProperties trueLicenseProperties) {
		this.trueLicenseProperties = trueLicenseProperties;
	}

	@ConditionalOnMissingBean
	@Bean
	public LicenseManager licenseManager() {
		log.debug(MSG_AUTOWIRED_TRUE_LICENSE_MANAGER);
		TrueLicenseConfig trueLicenseConfig = TrueLicenseConfig.builder()
			.keyPass(trueLicenseProperties.getKeyPass())
			.subject(trueLicenseProperties.getSubject())
			.licensePath(trueLicenseProperties.getLicensePath())
			.storePass(trueLicenseProperties.getStorePass())
			.keyPass(trueLicenseProperties.getKeyPass())
			.privateKeysStorePath(trueLicenseProperties.getPrivateKeysStorePath())
			.privateAlias(trueLicenseProperties.getPrivateAlias())
			.publicKeysStorePath(trueLicenseProperties.getPublicKeysStorePath())
			.publicAlias(trueLicenseProperties.getPublicAlias())
			.build();
		return new CustomLicenseManager(trueLicenseConfig);
	}
}
