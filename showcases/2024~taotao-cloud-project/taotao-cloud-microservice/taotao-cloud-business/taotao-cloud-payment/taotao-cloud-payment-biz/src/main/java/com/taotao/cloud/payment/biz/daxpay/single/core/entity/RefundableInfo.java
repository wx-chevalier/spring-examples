package com.taotao.cloud.payment.biz.daxpay.single.core.entity;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 可退款信息(不持久化,直接保存为json)
 * @author xxm
 * @since 2023/12/18
 */
@Data
@Accessors(chain = true)
public class RefundableInfo {
    /**
     * 通道
     * @see PayChannelEnum#getCode()
     */
    private String channel;

    /**
     * 可退款金额
      */
    private Integer amount;
}
