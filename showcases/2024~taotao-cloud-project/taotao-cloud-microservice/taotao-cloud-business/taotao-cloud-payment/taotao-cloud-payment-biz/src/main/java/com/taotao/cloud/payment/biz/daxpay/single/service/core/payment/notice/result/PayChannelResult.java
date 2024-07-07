package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.notice.result;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.code.PayWayEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 支付通道信息
 * @author xxm
 * @since 2024/1/7
 */
@Data
@Accessors(chain = true)
@Schema(title = "支付通道信息")
public class PayChannelResult {
    /**
     * 支付通道编码
     * @see PayChannelEnum#getCode()
     */
    @Schema(description = "支付通道编码")
    private String channel;

    /**
     * 支付方式编码
     * @see PayWayEnum
     */
    @Schema(description = "支付方式编码")
    private String way;

    @Schema(description = "支付金额")
    private Integer amount;

}
