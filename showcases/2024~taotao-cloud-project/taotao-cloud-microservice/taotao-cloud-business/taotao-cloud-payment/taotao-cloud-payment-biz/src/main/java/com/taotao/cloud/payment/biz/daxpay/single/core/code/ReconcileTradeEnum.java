package com.taotao.cloud.payment.biz.daxpay.single.core.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对账交易类型
 * @author xxm
 * @since 2024/1/23
 */
@Getter
@AllArgsConstructor
public enum ReconcileTradeEnum {

    PAY("pay","支付"),
    REFUND("refund","退款"),
    REVOKED("revoked","撤销");

    private final String code;
    private final String name;
}
