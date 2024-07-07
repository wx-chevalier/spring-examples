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

package com.taotao.cloud.sys.biz.shortlink.src.main.java.com.taotao.cloud.shortlink.biz.other.server.application.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    public static ResponseEntity notFound() {
        return ResponseEntity.status(404).body(notFound(""));
    }

    public static String notFound(String string) {
        JSONObject result = new JSONObject();
        result.put("code", 404);
        result.put("errMsg", "No found");
        return result.toString();
    }

    public static String serverError(String string) {
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("errMsg", " server busy");
        return result.toString();
    }
}
