package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.pay.strategy;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.exception.pay.PayFailureException;
import cn.bootx.platform.daxpay.param.channel.UnionPayParam;
import cn.bootx.platform.daxpay.service.core.channel.union.entity.UnionPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.union.service.UnionPayConfigService;
import cn.bootx.platform.daxpay.service.core.channel.union.service.UnionPayService;
import cn.bootx.platform.daxpay.service.func.AbsPayStrategy;
import cn.bootx.platform.daxpay.service.sdk.union.api.UnionPayKit;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 云闪付
 *
 * @author xxm
 * @since 2022/3/8
 */
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class UnionPayStrategy extends AbsPayStrategy {

    private final UnionPayService unionPayService;

    private final UnionPayConfigService unionPayConfigService;

    private UnionPayParam unionPayParam;

    private UnionPayConfig unionPayConfig;

    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.UNION_PAY;
    }
    /**
     * 支付前操作
     */
    @Override
    public void doBeforePayHandler() {
        try {
            // 云闪付参数验证
            Map<String, Object> channelParam = this.getPayParam().getExtraParam();
            if (CollUtil.isNotEmpty(channelParam)) {
                this.unionPayParam = BeanUtil.toBean(channelParam, UnionPayParam.class);
            }
            else {
                this.unionPayParam = new UnionPayParam();
            }
        }
        catch (JSONException e) {
            throw new PayFailureException("支付参数错误");
        }
        // 检查并获取云闪付支付配置
        this.unionPayConfig = unionPayConfigService.getAndCheckConfig();
        unionPayService.validation(this.getPayParam(), unionPayConfig);
    }

    /**
     * 发起支付操作
     */
    @Override
    public void doPayHandler() {
        UnionPayKit unionPayKit = unionPayConfigService.initPayService(unionPayConfig);
        unionPayService.pay(this.getOrder(), this.unionPayParam, unionPayKit);
    }

}
