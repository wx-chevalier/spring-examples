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

package org.ylzl.eden.flow.compose.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.ylzl.eden.flow.compose.processor.SpringBeanProcessorFactory;

/**
 * Spring Bean 注入流程处理器自动装配
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Configuration(proxyBeanMethods = false)
public class ProcessFactoryConfiguration {

	public static final String SPRING_BEAN_PROCESSOR_FACTORY_NAME = "springBeanProcessorFactory";

	private static final String AUTOWIRED_SPRING_BEAN_PROCESSOR_FACTORY = "Autowired SpringBeanProcessorFactory";

	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	@Bean
	public SpringBeanProcessorFactory springBeanProcessorFactory() {
		log.debug(AUTOWIRED_SPRING_BEAN_PROCESSOR_FACTORY);
		return new SpringBeanProcessorFactory();
	}
}
