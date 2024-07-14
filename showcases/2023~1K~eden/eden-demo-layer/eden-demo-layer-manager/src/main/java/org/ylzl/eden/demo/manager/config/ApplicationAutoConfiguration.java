package org.ylzl.eden.demo.manager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用自动装配
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@EnableConfigurationProperties({ApplicationProperties.class})
@Slf4j
@Configuration(proxyBeanMethods = false)
public class ApplicationAutoConfiguration {

}
