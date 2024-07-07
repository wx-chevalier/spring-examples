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

package com.taotao.cloud.wechat.biz.weixin.utils;

import com.joolun.weixin.constant.ConfigConstant;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author www.joolun.com 小程序工具类
 */
public class WxMaUtil {
    /**
     * 通过request获取appId
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getAppId(HttpServletRequest request) {
        String appId = request.getHeader(ConfigConstant.HEADER_APP_ID);
        return appId;
    }
}
