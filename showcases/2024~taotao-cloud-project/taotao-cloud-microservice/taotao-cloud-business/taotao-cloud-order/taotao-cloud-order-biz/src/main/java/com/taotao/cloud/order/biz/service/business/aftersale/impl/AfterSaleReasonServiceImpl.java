/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.order.biz.service.business.aftersale.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taotao.cloud.order.sys.model.page.aftersale.AfterSaleReasonPageQuery;
import com.taotao.cloud.order.biz.mapper.aftersale.IAfterSaleReasonMapper;
import com.taotao.cloud.order.biz.model.entity.aftersale.AfterSaleReason;
import com.taotao.cloud.order.biz.service.business.aftersale.IAfterSaleReasonService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 售后原因业务层实现
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 08:49:27
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AfterSaleReasonServiceImpl extends ServiceImpl<IAfterSaleReasonMapper, AfterSaleReason>
        implements IAfterSaleReasonService {

    @Override
    public List<AfterSaleReason> afterSaleReasonList(String serviceType) {
        LambdaQueryWrapper<AfterSaleReason> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AfterSaleReason::getServiceType, serviceType);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public Boolean editAfterSaleReason(AfterSaleReason afterSaleReason) {
        LambdaUpdateWrapper<AfterSaleReason> lambdaQueryWrapper = Wrappers.lambdaUpdate();
        lambdaQueryWrapper.eq(AfterSaleReason::getId, afterSaleReason.getId());
        lambdaQueryWrapper.set(AfterSaleReason::getReason, afterSaleReason.getReason());
        lambdaQueryWrapper.set(AfterSaleReason::getServiceType, afterSaleReason.getServiceType());
        this.update(lambdaQueryWrapper);
        return true;
    }

    @Override
    public IPage<AfterSaleReason> pageQuery(AfterSaleReasonPageQuery afterSaleReasonPageQuery) {
        LambdaQueryWrapper<AfterSaleReason> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(AfterSaleReason::getServiceType, afterSaleReasonPageQuery.getServiceType());
        return this.page(afterSaleReasonPageQuery.buildMpPage(), queryWrapper);
    }
}
