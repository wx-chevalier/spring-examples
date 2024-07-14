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
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StopWatch;
import org.ylzl.eden.commons.lang.time.DateUtils;
import org.ylzl.eden.spring.integration.truelicense.config.TrueLicenseConfig;

import java.io.File;

/**
 * 许可证安装服务
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
public class LicenseInstall implements InitializingBean, DisposableBean {

	private static final String STOP_WATCH_ID = "true-license";

	private final TrueLicenseConfig trueLicenseConfig;

	private final LicenseManager licenseManager;

	public LicenseInstall(
		TrueLicenseConfig trueLicenseConfig, LicenseManager licenseManager) {
		this.trueLicenseConfig = trueLicenseConfig;
		this.licenseManager = licenseManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("Starting install license");
		StopWatch watch = new StopWatch(STOP_WATCH_ID);
		watch.start();
		install();
		watch.stop();
		log.debug("Finished install license cost {} milliseconds", watch.getTotalTimeMillis());
	}

	@Override
	public void destroy() throws Exception {
		log.debug("Starting uninstall license");
		licenseManager.uninstall();
	}

	public LicenseContent install() throws Exception {
		File file = new File(trueLicenseConfig.getLicensePath());
		LicenseContent licenseContent;
		try {
			licenseManager.uninstall();
			licenseContent = licenseManager.install(file);
		} catch (Exception e) {
			log.error("Install license failed, caught exception: {}", e.getMessage(), e);
			throw e;
		}
		log.info(
			"Install license successed, valid from {} to {}",
			DateUtils.toDateTimeString(licenseContent.getNotBefore()),
			DateUtils.toDateTimeString(licenseContent.getNotAfter()));
		return licenseContent;
	}
}
