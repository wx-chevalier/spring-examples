package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.pay.task;

import cn.bootx.platform.common.core.exception.RepetitiveOperationException;
import cn.bootx.platform.daxpay.param.pay.PaySyncParam;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.pay.dao.PayExpiredTimeRepository;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.sync.service.PaySyncService;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * 待支付订单的状态同步, 先不进行启用
 * @author xxm
 * @since 2024/1/5
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayWaitOrderSyncTask {
    private final PayExpiredTimeRepository repository;

    private final PaySyncService paySyncService;

    private final LockTemplate lockTemplate;

    public void task(){
        log.debug("开始同步支付订单状态");
        // 从超时订单列表中获取到未超时的订单号
        Set<String> keys = repository.getNormalKeysBy30Day();
        for (String key : keys) {
            LockInfo lock = lockTemplate.lock("payment:sync:" + key,10000,200);
            if (Objects.isNull(lock)){
                throw new RepetitiveOperationException("支付同步处理中，请勿重复操作");
            }
            try {
                Long paymentId = Long.parseLong(key);
                PaySyncParam paySyncParam = new PaySyncParam();
                paySyncParam.setPaymentId(paymentId);
                // 执行网关同步, 网关同步时会对支付的进行状态的处理
                paySyncService.sync(paySyncParam);
            } catch (Exception e) {
                log.error("同步支付订单异常", e);
            } finally {
                lockTemplate.releaseLock(lock);
            }
        }

    }
}
