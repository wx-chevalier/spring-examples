package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.close.service;

import cn.bootx.platform.common.core.exception.RepetitiveOperationException;
import cn.bootx.platform.daxpay.code.PayStatusEnum;
import cn.bootx.platform.daxpay.exception.pay.PayFailureException;
import cn.bootx.platform.daxpay.param.payment.pay.PayCloseParam;
import cn.bootx.platform.daxpay.result.pay.PayCloseResult;
import cn.bootx.platform.daxpay.service.common.local.PaymentContextLocal;
import cn.bootx.platform.daxpay.service.core.order.pay.entity.PayOrder;
import cn.bootx.platform.daxpay.service.core.order.pay.service.PayOrderQueryService;
import cn.bootx.platform.daxpay.service.core.order.pay.service.PayOrderService;
import cn.bootx.platform.daxpay.service.core.payment.close.factory.PayCloseStrategyFactory;
import cn.bootx.platform.daxpay.service.core.payment.notice.service.ClientNoticeService;
import cn.bootx.platform.daxpay.service.core.record.close.entity.PayCloseRecord;
import cn.bootx.platform.daxpay.service.core.record.close.service.PayCloseRecordService;
import cn.bootx.platform.daxpay.service.func.AbsPayCloseStrategy;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 支付关闭和撤销服务
 * @author xxm
 * @since 2023/12/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayCloseService {
    private final PayOrderService payOrderService;
    private final PayOrderQueryService payOrderQueryService;
    private final PayCloseRecordService payCloseRecordService;
    private final ClientNoticeService clientsService;;

    private final LockTemplate lockTemplate;

    /**
     * 关闭支付
     */
    @Transactional(rollbackFor = Exception.class)
    public PayCloseResult close(PayCloseParam param){
        PayOrder payOrder = payOrderQueryService.findByBizOrOrderNo(param.getOrderNo(), param.getBizTradeNo())
                .orElseThrow(() -> new PayFailureException("未查询到支付订单"));
        LockInfo lock = lockTemplate.lock("payment:close:" + payOrder.getId(),10000, 50);
        if (Objects.isNull(lock)){
            throw new RepetitiveOperationException("支付订单已在关闭中，请勿重复发起");
        }
        try {
            return this.close(payOrder);
        } finally {
            lockTemplate.releaseLock(lock);
        }
    }

    /**
     * 关闭支付记录
     */
    private PayCloseResult close(PayOrder payOrder) {
        PayCloseResult result = new PayCloseResult();
        try {
            // 状态检查, 只有支付中可以进行取消支付
            if (!Objects.equals(payOrder.getStatus(), PayStatusEnum.PROGRESS.getCode())) {
                throw new PayFailureException("订单不是支付中, 无法进行关闭订单");
            }
            AbsPayCloseStrategy strategy = PayCloseStrategyFactory.create(payOrder.getChannel());
            // 设置支付订单
            strategy.setOrder(payOrder);
            // 关闭前准备
            strategy.doBeforeCloseHandler();
            // 执行关闭策略
            strategy.doCloseHandler();
            // 成功处理
            this.successHandler(payOrder);
            // 返回结果
            return result;
        } catch (Exception e) {
            log.error("关闭订单失败, id: {}:", payOrder.getId());
            log.error("关闭订单失败:", e);
            // 记录关闭失败的记录
            this.saveRecord(payOrder, false, e.getMessage());
            result.setCode("1").setMsg(e.getMessage());
            return result;
        }
    }

    /**
     * 成功后处理方法
     */
    private void successHandler(PayOrder payOrder){
        // 关闭订单
        payOrder.setStatus(PayStatusEnum.CLOSE.getCode())
                .setCloseTime(LocalDateTime.now());
        payOrderService.updateById(payOrder);
        // 发送通知
        clientsService.registerPayNotice(payOrder,null);
        this.saveRecord(payOrder,true,null);
    }

    /**
     * 保存关闭记录
     */
    private void saveRecord(PayOrder payOrder, boolean closed, String errMsg){
        String clientIp = PaymentContextLocal.get()
                .getRequestInfo()
                .getClientIp();
        PayCloseRecord record = new PayCloseRecord()
                .setOrderNo(payOrder.getOrderNo())
                .setBizOrderNo(payOrder.getOutOrderNo())
                .setChannel(payOrder.getChannel())
                .setClosed(closed)
                .setErrorMsg(errMsg)
                .setClientIp(clientIp);
        payCloseRecordService.saveRecord(record);
    }
}
