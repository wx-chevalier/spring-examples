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
package io.github.smart.cloud.common.pojo.util;

import io.github.smart.cloud.common.pojo.BasePageRequest;
import io.github.smart.cloud.common.pojo.dataobject.BasePageRequestDO;

/**
 * 分页工具类
 *
 * @author collin
 * @date 2021-10-30
 */
public class PageUtil {

    private PageUtil() {
    }

    /**
     * 设置limit分页参数
     *
     * @param basePageRequestDO
     * @param pageRequest
     * @return
     */
    public static BasePageRequestDO initLimitParams(BasePageRequestDO basePageRequestDO, BasePageRequest pageRequest) {
        basePageRequestDO.setOffset((pageRequest.getPageNum() - 1) * pageRequest.getPageSize());
        basePageRequestDO.setRows(pageRequest.getPageSize());
        return basePageRequestDO;
    }

}