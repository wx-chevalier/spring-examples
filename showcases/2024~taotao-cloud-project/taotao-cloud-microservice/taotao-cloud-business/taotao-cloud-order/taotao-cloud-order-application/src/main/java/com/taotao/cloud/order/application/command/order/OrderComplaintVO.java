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
import java.util.List;

/** 订单交易投诉VO */
@RecordBuilder
@Schema(description = "订单交易投诉VO")
public record OrderComplaintVO(
        @Schema(description = "投诉对话") List<OrderComplaintCommunicationVO> orderComplaintCommunications,
        @Schema(description = "投诉图片") String[] orderComplaintImages,
        @Schema(description = "申诉商家上传的图片") String[] appealImagesList,
        OrderComplaintBaseVO orderComplaintBase)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = -7013465343480854816L;
}
