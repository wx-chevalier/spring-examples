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

package com.taotao.cloud.operation.api.feign;

import com.taotao.cloud.common.constant.ServiceName;
import com.taotao.cloud.operation.api.feign.fallback.FeignArticleApiFallback;
import com.taotao.cloud.operation.api.model.vo.ArticleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程调用售后模块
 *
 * @author shuigedeng
 * @since 2020/5/2 16:42
 */
@FeignClient(
        contextId = "IFeignArticleService",
        value = ServiceName.TAOTAO_CLOUD_OPERATION,
        fallbackFactory = FeignArticleApiFallback.class)
public interface IFeignArticleApi {

    /**
     * 根据id查询文章详情
     *
     * @param id id
     * @return 文章详情
     * @since 2020/11/20 上午9:50
     */
    @GetMapping(value = "/withdraw/info/id/{id:[0-9]*}")
    ArticleVO getMemberSecurityUser(@PathVariable(value = "id") Long id);
}
