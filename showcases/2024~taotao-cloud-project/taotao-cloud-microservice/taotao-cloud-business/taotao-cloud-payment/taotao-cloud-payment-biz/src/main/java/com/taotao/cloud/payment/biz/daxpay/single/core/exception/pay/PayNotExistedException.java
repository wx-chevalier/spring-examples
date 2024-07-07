package com.taotao.cloud.payment.biz.daxpay.single.core.exception.pay;

import cn.bootx.platform.common.core.exception.BizException;
import cn.bootx.platform.daxpay.code.DaxPayErrorCode;

/**
 * 付款记录不存在
 *
 * @author xxm
 * @since 2020/12/8
 */
public class PayNotExistedException extends BizException {

    public PayNotExistedException() {
        super(DaxPayErrorCode.PAYMENT_RECORD_NOT_EXISTED, "付款记录不存在");
    }

}
