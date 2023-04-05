package com.cloud.files;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import io.micrometer.core.instrument.MeterRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@MapperScan("com.cloud.files.mapper")
@EnableFeignClients(basePackages = "com.cloud.feign")
@EnableDiscoveryClient
@SpringBootApplication
@EnableDistributedTransaction
public class FilesServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilesServerApplication.class, args);
	}

	@Value("${spring.application.name}")
	private String applicationName;
	@Bean
	MeterRegistryCustomizer<MeterRegistry> configurer() {
		return (registry) -> registry.config().commonTags("application", applicationName);
	}

}
