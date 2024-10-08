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

package com.taotao.cloud.operation.api.enums;

/**
 * 文章分类枚举
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-21 16:59:38
 */
public enum ArticleEnum {

    /** 关于我们 */
    ABOUT,
    /** 隐私政策 */
    PRIVACY_POLICY,
    /** 用户协议 */
    USER_AGREEMENT,
    /** 证照信息 */
    LICENSE_INFORMATION,
    /** 店铺入驻 */
    STORE_REGISTER,
    /** 其他文章 */
    OTHER;

    public String value() {
        return this.name();
    }
}
