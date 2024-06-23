/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.smart.cloud.starter.web.test.cases;

import io.github.smart.cloud.starter.web.test.prepare.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public abstract class AbstractTest {

    protected MockMvc mockMvc = null;
    @Autowired
    protected ApplicationContext applicationContext;

    @BeforeEach
    public void initMock() {
        // 添加过滤器
        Map<String, Filter> filterMap = applicationContext.getBeansOfType(Filter.class);
        Filter[] filters = new Filter[filterMap.size()];
        int i = 0;
        for (Map.Entry<String, Filter> entry : filterMap.entrySet()) {
            filters[i++] = entry.getValue();
        }

        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) applicationContext).addFilters(filters).build();
    }

}