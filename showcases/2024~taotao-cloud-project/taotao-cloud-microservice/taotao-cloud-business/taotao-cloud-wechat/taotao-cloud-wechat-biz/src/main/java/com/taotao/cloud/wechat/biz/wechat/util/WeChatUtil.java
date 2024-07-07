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

package com.taotao.cloud.wechat.biz.wechat.util;

import org.dromara.hutoolcore.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 * 微信工具类
 *
 * @author xxm
 * @since 2022/8/5
 */
@UtilityClass
public class WeChatUtil {

    /** 获取微信二维码链接的参数值 */
    public String getKeyByUrl(String url) {
        return StrUtil.subAfter(url, "/q/", true);
    }
}
