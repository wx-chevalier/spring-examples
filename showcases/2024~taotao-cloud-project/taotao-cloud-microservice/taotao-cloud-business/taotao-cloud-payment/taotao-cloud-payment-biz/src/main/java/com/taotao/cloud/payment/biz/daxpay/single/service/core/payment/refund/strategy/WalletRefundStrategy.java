package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.refund.strategy;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.param.channel.WalletPayParam;
import cn.bootx.platform.daxpay.service.core.channel.wallet.entity.Wallet;
import cn.bootx.platform.daxpay.service.core.channel.wallet.service.WalletPayService;
import cn.bootx.platform.daxpay.service.core.channel.wallet.service.WalletQueryService;
import cn.bootx.platform.daxpay.service.func.AbsRefundStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 钱包支付退款
 * @author xxm
 * @since 2023/7/5
 */
@Scope(SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class WalletRefundStrategy extends AbsRefundStrategy {

    private final WalletPayService walletPayService;

    private final WalletQueryService walletQueryService;

    private WalletPayParam walletPayParam;

    private Wallet wallet;

    /**
     * 策略标识
     *
     * @see PayChannelEnum
     */
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.WALLET;
    }


    /**
     * 退款前对处理
     */
    @Override
    public void doBeforeRefundHandler() {
        // 获取钱包
        this.wallet = walletQueryService.getWallet(this.walletPayParam);
    }

    /**
     * 退款操作
     */
    @Override
    public void doRefundHandler() {
        // 不包含异步支付, 则只在支付订单中进行扣减, 等待异步退款完成, 再进行退款
        walletPayService.refund(this.wallet, this.getRefundOrder().getAmount());
    }
}
