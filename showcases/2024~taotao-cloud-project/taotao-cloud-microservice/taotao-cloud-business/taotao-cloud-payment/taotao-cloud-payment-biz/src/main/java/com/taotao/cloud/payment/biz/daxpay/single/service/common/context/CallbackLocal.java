package com.taotao.cloud.payment.biz.daxpay.single.service.common.context;

import cn.bootx.platform.daxpay.code.RefundStatusEnum;
import cn.bootx.platform.daxpay.code.PayStatusEnum;
import cn.bootx.platform.daxpay.service.code.PayCallbackStatusEnum;
import cn.bootx.platform.daxpay.service.code.PaymentTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 回调信息上下文
 * @author xxm
 * @since 2024/1/24
 */
@Data
@Accessors(chain = true)
public class CallbackLocal {

    /** 回调参数内容 */
    private Map<String, String> callbackParam = new HashMap<>();

    /** 交易号 */
    private String tradeNo;

    /**
     * 第三方支付平台交易号
     */
    private String outTradeNo;

    /**
     * 通道
     */
    private String channel;

    /**
     * 三方支付系统返回状态
     * @see PayStatusEnum 支付状态
     * @see RefundStatusEnum 退款状态
     */
    private String outStatus;

    /** 金额(元) */
    private String amount;

    /** 完成时间(支付/退款) */
    private LocalDateTime finishTime;

    /** 修复号 */
    private String repairNo;

    /** 回调类型 */
    private PaymentTypeEnum callbackType;

    /**
     * 回调处理状态
     * @see PayCallbackStatusEnum
     */
    private PayCallbackStatusEnum callbackStatus = PayCallbackStatusEnum.SUCCESS;

    /** 提示信息 */
    private String msg;
}
