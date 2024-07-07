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

package com.taotao.cloud.order.api.feign.response;

import io.soabase.recordbuilder.core.RecordBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/** 订单vo */
@RecordBuilder
@Schema(description = "订单vo")
public record FeignOrderResponse(
        @Schema(description = "订单商品项目") List<FeignOrderItemResponse> orderItems,
        @Schema(description = "订单vo") FeignOrderBaseResponse orderBase)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 8808470688518188146L;

    // public OrderVO(OrderBaseVO order, List<OrderItem> orderItems) {
    // 	BeanUtil.copyProperties(order, this);
    // 	this.setOrderItems(orderItems);
    // }
}
