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

package com.taotao.cloud.member.application.event.member;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听售后日志事件
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-07 20:21:32
 */
@Component
public class MemberCreateEventListener {

    // @Autowired
    // private IAfterSaleLogService afterSaleLogService;

    @Async
    @EventListener(MemberCreateEvent.class)
    public void saveAfterSaleLog(MemberCreateEvent event) {
        // AfterSaleLog afterSaleLog = (AfterSaleLog) event.getSource();
        // afterSaleLogService.save(afterSaleLog);
    }
}
