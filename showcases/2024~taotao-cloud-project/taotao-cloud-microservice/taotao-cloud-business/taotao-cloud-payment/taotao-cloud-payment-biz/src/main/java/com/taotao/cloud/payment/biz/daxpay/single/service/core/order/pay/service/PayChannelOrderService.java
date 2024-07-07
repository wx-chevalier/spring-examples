package com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.service;

import cn.bootx.platform.common.core.exception.DataNotExistException;
import cn.bootx.platform.common.core.util.ResultConvertUtil;
import cn.bootx.platform.daxpay.code.RefundStatusEnum;
import cn.bootx.platform.daxpay.code.PayStatusEnum;
import cn.bootx.platform.daxpay.param.pay.PayChannelParam;
import com.taotao.cloud.payment.biz.daxpay.single.service.common.context.PayLocal;
import com.taotao.cloud.payment.biz.daxpay.single.service.common.local.PaymentContextLocal;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.dao.PayChannelOrderManager;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.entity.PayChannelOrder;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.pay.entity.PayOrder;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.refund.entity.RefundChannelOrder;
import com.taotao.cloud.payment.biz.daxpay.single.service.dto.order.pay.PayChannelOrderDto;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 支付订单关联通道服务
 * @author xxm
 * @since 2023/12/31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayChannelOrderService {
    private final PayChannelOrderManager channelOrderManager;

    /**
     * 根据支付ID查询列表
     */
    public List<PayChannelOrderDto> findAllByPaymentId(Long paymentId){
        return ResultConvertUtil.dtoListConvert(channelOrderManager.findAllByPaymentId(paymentId));
    }

    /**
     * 查询单条
     */
    public PayChannelOrderDto findById(Long id){
        return channelOrderManager.findById(id).map(PayChannelOrder::toDto).orElseThrow(() -> new DataNotExistException("通道支付订单未查到"));
    }

    /**
     * 切换支付订单关联的异步支付通道, 同时会设置是否支付完成状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void switchAsyncPayChannel(PayOrder payOrder, PayChannelParam payChannelParam){
        PayLocal payInfo = PaymentContextLocal.get().getPayInfo();
        // 是否支付完成
        PayStatusEnum payStatus = payInfo.isPayComplete() ? PayStatusEnum.SUCCESS : PayStatusEnum.PROGRESS;
        // 判断新发起的
        Optional<PayChannelOrder> payOrderChannelOpt = channelOrderManager.findByPaymentIdAndChannel(payOrder.getId(), payChannelParam.getChannel());
        // 扩展信息处理
        Map<String, Object> channelParam = payChannelParam.getChannelParam();
        String channelParamStr = null;
        if (Objects.nonNull(channelParam)){
            channelParamStr = JSONUtil.toJsonStr(channelParam);
        }
        if (!payOrderChannelOpt.isPresent()){
            PayChannelOrder payChannelOrder = new PayChannelOrder();
            // 替换原有的的支付通道信息
            payChannelOrder.setPayWay(payChannelParam.getWay())
                    .setPaymentId(payOrder.getId())
                    .setAsync(true)
                    .setChannel(payChannelParam.getChannel())
                    .setPayWay(payChannelParam.getWay())
                    .setAmount(payChannelParam.getAmount())
                    .setRefundableBalance(payChannelParam.getAmount())
                    .setPayTime(LocalDateTime.now())
                    .setChannelExtra(channelParamStr)
                    .setStatus(payStatus.getCode());
            channelOrderManager.deleteByPaymentIdAndAsync(payOrder.getId());
            channelOrderManager.save(payChannelOrder);
            payInfo.getPayChannelOrders().add(payChannelOrder);
        } else {
            // 更新支付通道信息
            payOrderChannelOpt.get()
                    .setPayWay(payChannelParam.getWay())
                    .setPayTime(LocalDateTime.now())
                    .setChannelExtra(channelParamStr)
                    .setStatus(payStatus.getCode());
            channelOrderManager.updateById(payOrderChannelOpt.get());
            payInfo.getPayChannelOrders().add(payOrderChannelOpt.get());
        }
    }

    /**
     * 更新异步支付通道退款余额和状态
     */
    public void updateAsyncPayRefund(PayChannelOrder payChannelOrder, RefundChannelOrder refundChannelOrder){
        // 支付通道订单状态
        if (Objects.equals(refundChannelOrder.getStatus(), RefundStatusEnum.SUCCESS.getCode())){
            // 如果可退金额为0说明已经全部退款
            PayStatusEnum status = payChannelOrder.getRefundableBalance() == 0 ? PayStatusEnum.REFUNDED : PayStatusEnum.PARTIAL_REFUND;
            payChannelOrder.setStatus(status.getCode());
            refundChannelOrder.setRefundTime(LocalDateTime.now());
        } else {
            payChannelOrder.setStatus(PayStatusEnum.REFUNDING.getCode());
        }
    }

}
