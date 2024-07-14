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

package com.taotao.cloud.message.biz.austin.handler.script;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * sendAccount->SmsScript的映射关系
 *
 * @author 3y
 */
@Component
public class SmsScriptHolder {

    private Map<String, SmsScript> handlers = new HashMap<>(8);

    public void putHandler(String scriptName, SmsScript handler) {
        handlers.put(scriptName, handler);
    }

    public SmsScript route(String scriptName) {
        return handlers.get(scriptName);
    }
}