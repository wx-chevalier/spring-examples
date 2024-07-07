package com.taotao.cloud.payment.biz.daxpay.single.core.param.payment.allocation;

import cn.bootx.platform.daxpay.param.PaymentCommonParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分账完结参数
 * @author xxm
 * @since 2024/4/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(title = "分账请求参数")
public class AllocationFinishParam extends PaymentCommonParam {

    @Schema(description = "分账订单ID")
    private Long orderId;

    @Schema(description = "分账单号")
    private String allocationNo;
}
