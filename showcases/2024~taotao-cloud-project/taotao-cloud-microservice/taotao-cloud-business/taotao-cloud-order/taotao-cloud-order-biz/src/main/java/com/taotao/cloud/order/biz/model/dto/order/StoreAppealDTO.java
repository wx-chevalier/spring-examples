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

package com.taotao.cloud.order.biz.model.dto.order;

import io.soabase.recordbuilder.core.RecordBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;

/**
 * 订单交易投诉VO
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 09:19:47
 */
@RecordBuilder
@Schema(description = "订单交易投诉VO")
public record StoreAppealDTO(
        @Schema(description = "投诉id") Long orderComplaintId,
        @Schema(description = "申诉商家内容") String appealContent,
        @Schema(description = "申诉商家上传的图片") String appealImages)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 8808470688518188146L;
}
