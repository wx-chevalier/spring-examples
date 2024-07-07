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

package com.taotao.cloud.stock.biz.domain.tenant.model.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * 租户id
 *
 * @author shuigedeng
 * @since 2021-02-09
 */
public class TenantId implements ValueObject<TenantId> {

    /** 平台租户 */
    public static final String PLATFORM_TENANT = "1";

    private String id;

    public TenantId(final String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("租户id不能为空");
        }
        this.id = id;
    }

    public boolean isPlatformId() {
        return id != null && PLATFORM_TENANT.equals(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean sameValueAs(TenantId other) {
        return other != null && this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
