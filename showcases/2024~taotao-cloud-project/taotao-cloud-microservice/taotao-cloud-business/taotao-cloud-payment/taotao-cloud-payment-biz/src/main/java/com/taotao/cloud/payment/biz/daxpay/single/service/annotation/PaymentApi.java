package com.taotao.cloud.payment.biz.daxpay.single.service.annotation;

import java.lang.annotation.*;

/**
 * 支付接口标识，
 * 支付方法至少有一个参数，并且需要签名支付参数需要放在第一位
 * 返回对象必须为 ResResult<T extends PaymentCommonResult> 格式
 * @author xxm
 * @since 2023/12/22
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PaymentApi {

    /**
     * 支付接口编码
     */
    String value();
}
