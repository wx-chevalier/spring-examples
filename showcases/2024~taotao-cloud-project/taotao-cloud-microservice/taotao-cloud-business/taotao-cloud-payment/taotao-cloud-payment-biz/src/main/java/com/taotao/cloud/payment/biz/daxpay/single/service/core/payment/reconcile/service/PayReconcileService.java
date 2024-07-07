package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.reconcile.service;

import cn.bootx.platform.common.core.exception.DataNotExistException;
import com.taotao.cloud.payment.biz.daxpay.single.service.common.local.PaymentContextLocal;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.reconcile.dao.PayReconcileDetailManager;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.reconcile.entity.PayReconcileDetail;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.reconcile.entity.PayReconcileOrder;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.order.reconcile.service.PayReconcileOrderService;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.reconcile.factory.PayReconcileStrategyFactory;
import com.taotao.cloud.payment.biz.daxpay.single.service.func.AbsReconcileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付对账单下载服务
 * @author xxm
 * @since 2024/1/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayReconcileService {
    private final PayReconcileOrderService reconcileOrderService;
    private final PayReconcileDetailManager reconcileDetailManager;

    /**
     * 下载对账单并进行保存
     */
    public void downAndSave(Long reconcileOrderId) {
        PayReconcileOrder payReconcileOrder = reconcileOrderService.findById(reconcileOrderId)
                .orElseThrow(() -> new DataNotExistException("未找到对账订单"));
        this.downAndSave(payReconcileOrder);
    }

    /**
     * 下载对账单并进行保存
     */
    public void downAndSave(PayReconcileOrder recordOrder) {
        // 构建对账策略
        AbsReconcileStrategy absReconcileStrategy = PayReconcileStrategyFactory.create(recordOrder.getChannel());
        absReconcileStrategy.initParam(recordOrder);
        absReconcileStrategy.doBeforeHandler();
        try {
            absReconcileStrategy.downAndSave();
            recordOrder.setDown(true);
            reconcileOrderService.update(recordOrder);
        } catch (Exception e) {
            log.error("下载对账单异常", e);
            recordOrder.setErrorMsg("原因: " + e.getMessage());
            reconcileOrderService.update(recordOrder);
            throw new RuntimeException(e);
        }
        // 保存转换后的通用结构对账单
        List<PayReconcileDetail> reconcileDetails = PaymentContextLocal.get()
                .getReconcileInfo()
                .getReconcileDetails();
        reconcileDetailManager.saveAll(reconcileDetails);
    }

}
