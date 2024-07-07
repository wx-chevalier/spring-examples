package com.taotao.cloud.payment.biz.daxpay.single.service.core.system.config.dao;

import cn.bootx.platform.common.mybatisplus.base.MpIdEntity;
import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import cn.bootx.platform.daxpay.service.core.system.config.entity.PayApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 支付开放接口管理
 * @author xxm
 * @since 2023/12/22
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PayApiConfigManager extends BaseManager<PayApiConfigMapper, PayApiConfig> {

    /**
     * 根据code查询
     */
    public Optional<PayApiConfig> findByCode(String code){
        return findByField(PayApiConfig::getCode,code);
    }

    /**
     * 根据api查询
     */
    public Optional<PayApiConfig> findByApi(String api){
        return findByField(PayApiConfig::getApi,api);
    }

    /**
     * 查询全部
     */
    @Override
    public List<PayApiConfig> findAll() {
        return lambdaQuery().orderByAsc(MpIdEntity::getId).list();
    }
}
