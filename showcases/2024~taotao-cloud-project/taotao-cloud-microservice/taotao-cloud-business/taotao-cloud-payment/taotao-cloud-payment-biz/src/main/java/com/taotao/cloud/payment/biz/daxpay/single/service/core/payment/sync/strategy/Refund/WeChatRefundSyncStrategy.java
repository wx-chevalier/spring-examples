package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.sync.strategy.Refund;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.service.core.channel.wechat.entity.WeChatPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPayConfigService;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPaySyncService;
import cn.bootx.platform.daxpay.service.core.payment.sync.result.RefundSyncResult;
import cn.bootx.platform.daxpay.service.func.AbsRefundSyncStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 微信退款同步策略
 * @author xxm
 * @since 2024/1/29
 */
@Scope(SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class WeChatRefundSyncStrategy extends AbsRefundSyncStrategy {
    private final WeChatPaySyncService weChatPaySyncService;
    private final WeChatPayConfigService weChatPayConfigService;

    /**
     * 策略标识
     */
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.WECHAT;
    }

    /**
     * 异步支付单与支付网关进行状态比对后的结果
     */
    @Override
    public RefundSyncResult doSyncStatus() {
        WeChatPayConfig config = weChatPayConfigService.getConfig();
        return weChatPaySyncService.syncRefundStatus(this.getRefundOrder(), config);
    }

}
