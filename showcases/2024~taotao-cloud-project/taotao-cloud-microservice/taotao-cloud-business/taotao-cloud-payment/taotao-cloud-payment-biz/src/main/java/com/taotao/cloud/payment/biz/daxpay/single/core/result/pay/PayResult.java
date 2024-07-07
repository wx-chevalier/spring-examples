package com.taotao.cloud.payment.biz.daxpay.single.core.result.pay;

import cn.bootx.platform.daxpay.result.PaymentCommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 统一下单响应参数
 * @author xxm
 * @since 2023/12/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "统一下单响应参数")
public class PayResult extends PaymentCommonResult {

    /** 商户订单号 */
    @Schema(description = "商户订单号")
    private String bizOrderNo;

    /** 订单号 */
    @Schema(description = "订单号")
    private String orderNo;

    /** 支付状态 */
    @Schema(description = "支付状态")
    private String status;

    /** 支付参数体(通常用于发起支付的参数) */
    @Schema(description = "支付参数体")
    private String payBody;
}
