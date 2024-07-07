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

package com.taotao.cloud.order.application.command.order;

import io.soabase.recordbuilder.core.RecordBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;

/**
 * 订单交易投诉通信表
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-21 16:59:38
 */
@RecordBuilder
@Schema(description = "订单交易投诉通信VO")
public record OrderComplaintCommunicationBaseVO(
        @Schema(description = "投诉id") Long complainId,
        @Schema(description = "对话内容") String content,
        @Schema(description = "所属，买家/卖家") String owner,
        @Schema(description = "对话所属名称") String ownerName,
        @Schema(description = "对话所属id,卖家id/买家id") Long ownerId)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -2384351827382795547L;
}
