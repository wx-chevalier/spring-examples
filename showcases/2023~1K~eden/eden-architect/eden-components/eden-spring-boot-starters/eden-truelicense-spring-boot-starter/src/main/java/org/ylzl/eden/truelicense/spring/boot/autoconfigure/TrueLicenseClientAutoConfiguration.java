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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;
import org.ylzl.eden.truelicense.spring.boot.env.TrueLicenseProperties;
import org.ylzl.eden.spring.integration.truelicense.config.TrueLicenseConfig;
import org.ylzl.eden.spring.integration.truelicense.core.LicenseInstall;
import org.ylzl.eden.spring.integration.truelicense.core.LicenseVerify;

/**
 * TrueLicense 客户端配置
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@ConditionalOnProperty(value = TrueLicenseProperties.PREFIX + ".client.enabled")
@Import(TrueLicenseManagerConfiguration.class)
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Configuration(proxyBeanMethods = false)
public class TrueLicenseClientAutoConfiguration {

	private static final String MSG_AUTOWIRED_TRUE_LICENSE_INSTALL =
		"Autowired TureLicense install service";

	private static final String MSG_AUTOWIRED_TRUE_LICENSE_VERIFY =
		"Autowired TureLicense verify service";

	private final LicenseManager licenseManager;

	private final TrueLicenseProperties trueLicenseProperties;

	public TrueLicenseClientAutoConfiguration(LicenseManager licenseManager, TrueLicenseProperties trueLicenseProperties) {
		this.licenseManager = licenseManager;
		this.trueLicenseProperties = trueLicenseProperties;
	}

	@ConditionalOnMissingBean
	@Bean
	public LicenseInstall licenseInstall() {
		log.debug(MSG_AUTOWIRED_TRUE_LICENSE_INSTALL);
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
		return new LicenseInstall(trueLicenseConfig, licenseManager);
	}

	@ConditionalOnMissingBean
	@Bean
	public LicenseVerify licenseVerify() {
		log.debug(MSG_AUTOWIRED_TRUE_LICENSE_VERIFY);
		return new LicenseVerify(licenseManager);
	}
}
