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

package com.taotao.cloud.stock.biz.domain.role.model.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * 角色名称
 *
 * @author shuigedeng
 * @since 2021-02-08
 */
public class RoleName implements ValueObject<RoleName> {

    /** 租户管理员角色名称 */
    public static final String TENANT_ADMIN = "租户管理";

    private String name;

    public RoleName(final String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("角色名称不能为空");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean sameValueAs(RoleName other) {
        return other != null && this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
