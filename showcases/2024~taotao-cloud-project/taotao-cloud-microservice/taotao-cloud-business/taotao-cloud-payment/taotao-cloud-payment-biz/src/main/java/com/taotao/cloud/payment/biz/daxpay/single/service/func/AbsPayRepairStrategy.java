package com.taotao.cloud.payment.biz.daxpay.single.service.func;

import cn.bootx.platform.daxpay.service.core.order.pay.entity.PayOrder;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付订单修复策略
 * 1. 异步支付方式要实现所有的修复方法
 * 2. 同步支付通常只需要实现必须得修复方法即可(关闭订单)
 * @author xxm
 * @since 2023/12/27
 */
@Getter
@Setter
public abstract class AbsPayRepairStrategy implements PayStrategy{

    /** 支付订单 */
    private PayOrder order = null;

    /**
     * 修复前处理
     */
    public void doBeforeHandler(){

    }

    /**
     * 关闭三方系统的支付
     */
    public abstract void doCloseRemoteHandler();

}
