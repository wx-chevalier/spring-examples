/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.salesmanager.shop.spring.cloud.service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * TODO Comment
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since TODO
 */
@EnableAutoConfiguration
@EnableFeignClients(clients = HelloWorldService.class)
@RestController
public class ServiceClientApplication {

    private static final Logger log = LoggerFactory.getLogger(ServiceClientApplication.class);

    @Autowired
    private HelloWorldService helloWorldService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String index() {
        String message = helloWorldService.helloWorld();
        log.info(message);
        return message;
    }

    @RequestMapping("/rest")
    public String rest() {
        String message =  restTemplate.getForObject("http://service-provider/hello/world", String.class);
        log.info(message);
        return message;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceClientApplication.class)
                .run("--spring.config.additional-location=classpath:/META-INF/service-client.yaml");

    }
}
