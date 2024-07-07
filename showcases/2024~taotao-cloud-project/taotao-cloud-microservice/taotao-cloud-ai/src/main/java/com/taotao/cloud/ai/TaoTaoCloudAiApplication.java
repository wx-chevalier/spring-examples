/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
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

package com.taotao.cloud.ai;

import com.taotao.cloud.common.utils.common.PropertyUtils;
import com.taotao.cloud.core.startup.StartupSpringApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ai系统中心
 *
 * @author shuigedeng
 * @version 2022.03
 * @since 2020/11/26 下午7:55
 */
@SpringBootApplication
public class TaoTaoCloudAiApplication {

    public static void main(String[] args) {

		new StartupSpringApplication(TaoTaoCloudAiApplication.class)
			.setTtcBanner()
			.setTtcProfileIfNotExists("dev")
			.setTtcApplicationProperty("taotao-cloud-ai")
			.setTtcAllowBeanDefinitionOverriding(true)
			.run(args);


        // try {
        //    SpringApplication.run(TaoTaoCloudMonitorApplication.class, args);
        // } catch (Throwable e) {
        //    LogUtils.error(e);
        // }
    }
}
