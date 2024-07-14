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

package org.ylzl.eden.spring.integration.truelicense.core;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.commons.lang.ObjectUtils;
import org.ylzl.eden.commons.lang.StringUtils;
import org.ylzl.eden.commons.lang.time.DateUtils;
import org.ylzl.eden.spring.integration.truelicense.model.CustomLicenseContent;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.util.Date;

/**
 * 许可证服务存储
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
public class LicenseStore {

	private static final X500Principal DEFAULT_HOLDER_AND_ISSUER =
		new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");

	private static final String DEFAULT_CONSUMER_TYPE = "user";

	private final LicenseManager licenseManager;

	public LicenseStore(LicenseManager licenseManager) {
		this.licenseManager = licenseManager;
	}

	public boolean store(CustomLicenseContent customLicenseContent) {
		// 如果没有设置类型，按用户类型设置
		if (StringUtils.isNull(customLicenseContent.getConsumerType())) {
			customLicenseContent.setConsumerType(DEFAULT_CONSUMER_TYPE);
		}

		// 签发时间默认为当前系统时间
		customLicenseContent.setIssued(new Date());

		// 如果没有设置生效时间，按签发时间生效
		if (ObjectUtils.isNull(customLicenseContent.getNotBefore())) {
			customLicenseContent.setNotBefore(customLicenseContent.getIssued());
		}

		// 如果没有设置到期时间，默认为 1 个月有效期
		if (ObjectUtils.isNull(customLicenseContent.getNotAfter())) {
			customLicenseContent.setNotAfter(
				DateUtils.addMonths(customLicenseContent.getNotBefore(), 1));
		}

		LicenseContent licenseContent = new LicenseContent();
		licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
		licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);
		if (customLicenseContent.getSubject() != null) {
			licenseContent.setSubject(customLicenseContent.getSubject());
		}
		if (customLicenseContent.getIssued() != null) {
			licenseContent.setIssued(customLicenseContent.getIssued());
		}
		if (customLicenseContent.getNotBefore() != null) {
			licenseContent.setNotBefore(customLicenseContent.getNotBefore());
		}
		if (customLicenseContent.getNotAfter() != null) {
			licenseContent.setNotAfter(customLicenseContent.getNotAfter());
		}
		if (customLicenseContent.getConsumerType() != null) {
			licenseContent.setConsumerType(customLicenseContent.getConsumerType());
		}
		if (customLicenseContent.getConsumerAmount() != null) {
			licenseContent.setConsumerAmount(customLicenseContent.getConsumerAmount());
		}
		if (customLicenseContent.getInfo() != null) {
			licenseContent.setInfo(customLicenseContent.getInfo());
		}

		File file = new File(customLicenseContent.getLicensePath());
		try {
			licenseManager.store(licenseContent, file);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
