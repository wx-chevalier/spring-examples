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

package com.taotao.cloud.wechat.infrastructure.persistent.dict.repository.cls;

import com.taotao.cloud.web.base.repository.BaseClassSuperRepository;
import com.taotao.cloud.wechat.infrastructure.persistent.dict.po.DictItemPO;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

/**
 * CompanyMapper
 *
 * @author shuigedeng
 * @version 2022.03
 * @since 2021/10/13 22:50
 */
@Repository
public class DictItemRepository extends BaseClassSuperRepository<DictItemPO, Long> {

    public DictItemRepository(EntityManager em) {
        super(DictItemPO.class, em);
    }
}
