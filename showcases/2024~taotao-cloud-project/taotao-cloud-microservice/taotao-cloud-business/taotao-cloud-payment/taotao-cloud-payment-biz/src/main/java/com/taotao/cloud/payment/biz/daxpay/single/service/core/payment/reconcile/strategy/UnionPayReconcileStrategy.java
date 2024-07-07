package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.reconcile.strategy;

import cn.bootx.platform.common.core.util.LocalDateTimeUtil;
import cn.bootx.platform.daxpay.code.PayChannelEnum;
import cn.bootx.platform.daxpay.service.core.channel.union.entity.UnionPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.union.service.UnionPayConfigService;
import cn.bootx.platform.daxpay.service.core.channel.union.service.UnionPayReconcileService;
import cn.bootx.platform.daxpay.service.core.payment.reconcile.domain.GeneralReconcileRecord;
import cn.bootx.platform.daxpay.service.func.AbsReconcileStrategy;
import cn.bootx.platform.daxpay.service.sdk.union.api.UnionPayKit;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 *
 * @author xxm
 * @since 2024/3/8
 */
@Slf4j
@Service
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class UnionPayReconcileStrategy extends AbsReconcileStrategy {

    private final UnionPayConfigService configService;

    private final UnionPayReconcileService reconcileService;

    private UnionPayKit unionPayKit;

    /**
     * 对账前处理, 主要是初始化支付SDK配置
     */
    @Override
    public void doBeforeHandler() {
        UnionPayConfig config = configService.getConfig();
        // 测试环境使用测试号
        if (config.isSandbox()) {
            config.setMachId("700000000000001");
        }
        this.unionPayKit = configService.initPayService(config);
    }

    /**
     * 上传对账单解析并保存
     */
    @SneakyThrows
    @Override
    public void upload(MultipartFile file) {
        reconcileService.upload(file.getBytes());
    }

    /**
     * 下载对账单到本地进行保存
     */
    @Override
    public void downAndSave() {
        Date date = DateUtil.date(this.getRecordOrder().getDate());
        reconcileService.downAndSave(date, this.unionPayKit);
    }

    /**
     * 获取通用对账对象, 将流水记录转换为对账对象
     */
    @Override
    public List<GeneralReconcileRecord> getGeneralReconcileRecord() {
        // 查询流水
        LocalDateTime localDateTime = LocalDateTimeUtil.date2DateTime(this.getRecordOrder().getDate());
        LocalDateTime start = LocalDateTimeUtil.beginOfDay(localDateTime);
        LocalDateTime end = LocalDateTimeUtil.endOfDay(localDateTime);
        return null;
    }

    /**
     * 策略标识
     */
    @Override
    public PayChannelEnum getChannel() {
        return PayChannelEnum.UNION_PAY;
    }
}
