package com.taotao.cloud.payment.biz.daxpay.single.core.param.payment.allocation;

import cn.bootx.platform.daxpay.param.PaymentCommonParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 开始分账请求参数
 * @author xxm
 * @since 2024/4/6
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(title = "开始分账请求参数")
public class AllocationStartParam extends PaymentCommonParam {

    @Schema(description = "商户分账单号")
    private String bizAllocationNo;

    @Schema(description = "支付订单号")
    private String orderNo;

    @Schema(description = "商户订单号")
    private String bizOrderNo;

    @Schema(description = "分账描述")
    private String description;

    /**
     * 不传输分账组使用默认分账组进行分账
     */
    @Schema(description = "分账组ID")
    private Long allocationGroupId;

}
