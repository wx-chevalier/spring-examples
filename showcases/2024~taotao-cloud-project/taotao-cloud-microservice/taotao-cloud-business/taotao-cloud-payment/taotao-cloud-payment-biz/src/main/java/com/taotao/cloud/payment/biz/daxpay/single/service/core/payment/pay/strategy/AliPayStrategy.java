package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.pay.strategy;

import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.exception.pay.PayFailureException;
import cn.bootx.platform.daxpay.param.channel.AliPayParam;
import cn.bootx.platform.daxpay.service.core.channel.alipay.entity.AliPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.alipay.service.AliPayConfigService;
import cn.bootx.platform.daxpay.service.core.channel.alipay.service.AliPayService;
import cn.bootx.platform.daxpay.service.func.AbsPayStrategy;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 支付宝支付
 * 注意: channelOrder对象需要单独处理, 直接获取会空指针
 * @author xxm
 * @since 2021/2/27
 */
@Scope(SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class AliPayStrategy extends AbsPayStrategy {

    private final AliPayService aliPayService;

    private final AliPayConfigService alipayConfigService;

    private AliPayConfig alipayConfig;

    private AliPayParam aliPayParam;

    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.ALI;
    }

    /**
     * 支付前操作
     */
    @Override
    public void doBeforePayHandler() {
        try {
            // 支付宝参数验证
            Map<String, Object> channelParam = this.getPayParam().getExtraParam();
            if (CollUtil.isNotEmpty(channelParam)) {
                this.aliPayParam = BeanUtil.toBean(channelParam, AliPayParam.class);
            }
            else {
                this.aliPayParam = new AliPayParam();
            }
        }
        catch (JSONException e) {
            throw new PayFailureException("支付参数错误");
        }
        // 检查并获取支付宝支付配置
        this.initAlipayConfig();
        // 支付宝相关校验
        aliPayService.validation(this.getPayParam(), alipayConfig);
    }

    /**
     * 发起支付操作
     */
    @Override
    public void doPayHandler() {
        aliPayService.pay(this.getOrder(), this.aliPayParam, this.alipayConfig);
    }

    /**
     * 初始化支付宝配置信息
     */
    private void initAlipayConfig() {
        // 获取并初始化支付宝支付配置
        this.alipayConfig = alipayConfigService.getAndCheckConfig();
        alipayConfigService.initConfig(this.alipayConfig);
    }

}
