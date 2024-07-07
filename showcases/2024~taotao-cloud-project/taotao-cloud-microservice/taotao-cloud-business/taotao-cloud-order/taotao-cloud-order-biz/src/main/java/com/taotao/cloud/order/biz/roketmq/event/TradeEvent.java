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

package com.taotao.cloud.order.biz.roketmq.event;

import com.taotao.cloud.order.sys.model.dto.cart.TradeDTO;

/**
 * 订单创建消息
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-05-16 17:34:02
 */
public interface TradeEvent {

    /**
     * 订单创建
     *
     * @param tradeDTO 交易
     * @since 2022-05-16 17:34:02
     */
    void orderCreate(TradeDTO tradeDTO);
}
