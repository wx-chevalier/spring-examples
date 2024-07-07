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

package com.taotao.cloud.order.biz.service.business.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.order.sys.model.dto.order.OrderReceiptDTO;
import com.taotao.cloud.order.sys.model.page.order.ReceiptPageQuery;
import com.taotao.cloud.order.biz.mapper.order.IReceiptMapper;
import com.taotao.cloud.order.biz.model.entity.order.Receipt;
import com.taotao.cloud.order.biz.service.business.order.IReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 发票业务层实现
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 08:55:14
 */
@AllArgsConstructor
@Service
public class ReceiptServiceImpl extends ServiceImpl<IReceiptMapper, Receipt> implements IReceiptService {

    @Override
    public IPage<OrderReceiptDTO> pageQuery(ReceiptPageQuery receiptPageQuery) {
        return this.baseMapper.getReceipt(receiptPageQuery.buildMpPage(), receiptPageQuery.wrapper());
    }

    @Override
    public Receipt getByOrderSn(String orderSn) {
        LambdaQueryWrapper<Receipt> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Receipt::getOrderSn, orderSn);
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public Receipt getDetail(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean saveReceipt(Receipt receipt) {
        LambdaQueryWrapper<Receipt> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Receipt::getReceiptTitle, receipt.getReceiptTitle());
        queryWrapper.eq(Receipt::getMemberId, receipt.getMemberId());
        if (receipt.getId() != null) {
            queryWrapper.ne(Receipt::getId, receipt.getId());
        }
        if (this.getOne(queryWrapper) == null) {
            this.save(receipt);
        }
        return true;
    }

    @Override
    public Receipt invoicing(Long receiptId) {
        // 根据id查询发票信息
        Receipt receipt = this.getById(receiptId);
        if (receipt != null) {
            receipt.setReceiptStatus(1);
            this.saveOrUpdate(receipt);
            return receipt;
        }
        throw new BusinessException(ResultEnum.USER_RECEIPT_NOT_EXIST);
    }
}
