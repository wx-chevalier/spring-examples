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

package com.taotao.cloud.order.api.feign;

import com.taotao.cloud.common.constant.ServiceName;
import com.taotao.cloud.common.model.PageQuery;
import com.taotao.cloud.common.model.PageResult;
import com.taotao.cloud.order.api.feign.fallback.FeignStoreFlowFallback;
import com.taotao.cloud.order.api.feign.fallback.FeignTradeApiFallback;
import com.taotao.cloud.order.api.feign.response.FeignTradeResponse;
import com.taotao.cloud.order.api.feign.response.FeingStoreFlowResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程调用订单模块
 *
 * @author shuigedeng
 * @since 2020/5/2 16:42
 */
@FeignClient(value = ServiceName.TAOTAO_CLOUD_ORDER, fallbackFactory = FeignStoreFlowFallback.class)
public interface IFeignStoreFlowApi {

    @GetMapping(value = "/trade")
	FeignTradeResponse getBySn(String sn);

    @GetMapping(value = "/getStoreFlow")
    PageResult<FeingStoreFlowResponse> getStoreFlow(String id, String flowType, PageQuery PageQuery);

    @GetMapping(value = "/getDistributionFlow")
    PageResult<FeingStoreFlowResponse> getDistributionFlow(String id, PageQuery PageQuery);
}
