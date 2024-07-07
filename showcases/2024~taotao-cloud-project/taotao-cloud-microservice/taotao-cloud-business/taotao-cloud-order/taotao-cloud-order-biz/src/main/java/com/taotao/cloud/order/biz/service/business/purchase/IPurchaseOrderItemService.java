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

package com.taotao.cloud.order.biz.service.business.purchase;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taotao.cloud.order.biz.model.entity.purchase.PurchaseOrderItem;
import java.util.List;

/**
 * 采购单子内容业务层
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 08:55:27
 */
public interface IPurchaseOrderItemService extends IService<PurchaseOrderItem> {

    /**
     * 添加采购单子内容
     *
     * @param purchaseOrderId 采购单ID
     * @param purchaseOrderItemList 采购单子内容列表
     * @return boolean
     * @since 2022-04-28 08:55:27
     */
    boolean addPurchaseOrderItem(String purchaseOrderId, List<PurchaseOrderItem> purchaseOrderItemList);
}
