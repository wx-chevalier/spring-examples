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

package com.taotao.cloud.workflow.biz.common.model.form.conbilling;

import java.math.BigDecimal;
import lombok.Data;

/** 合同开票流程 */
@Data
public class ConBillingInfoVO {
    @Schema(description = "主键id")
    private String id;

    @Schema(description = "相关附件")
    private String fileJson;

    @Schema(description = "开户账号")
    private String amount;

    @Schema(description = "紧急程度")
    private Integer flowUrgent;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "关联名称")
    private String conName;

    @Schema(description = "开票人")
    private String drawer;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "开票日期")
    private Long billDate;

    @Schema(description = "发票地址")
    private String invoAddress;

    @Schema(description = "流程标题")
    private String flowTitle;

    @Schema(description = "开户银行")
    private String bank;

    @Schema(description = "开票金额")
    private BigDecimal billAmount;

    @Schema(description = "付款金额")
    private BigDecimal payAmount;

    @Schema(description = "税号")
    private String taxId;

    @Schema(description = "发票号")
    private String invoiceId;

    @Schema(description = "流程主键")
    private String flowId;

    @Schema(description = "流程单据")
    private String billNo;
}
