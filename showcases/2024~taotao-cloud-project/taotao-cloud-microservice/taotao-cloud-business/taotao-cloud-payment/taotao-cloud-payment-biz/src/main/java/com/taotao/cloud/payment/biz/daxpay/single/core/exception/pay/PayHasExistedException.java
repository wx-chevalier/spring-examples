package com.taotao.cloud.payment.biz.daxpay.single.core.exception.pay;

import cn.bootx.platform.common.core.exception.BizException;
import cn.bootx.platform.daxpay.code.DaxPayErrorCode;

/**
 * 付款已存在
 *
 * @author xxm
 * @since 2020/12/8
 */
public class PayHasExistedException extends BizException {

    public PayHasExistedException() {
        super(DaxPayErrorCode.PAYMENT_HAS_EXISTED, "付款已存在");
    }

}
