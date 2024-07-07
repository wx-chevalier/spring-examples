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

package com.taotao.cloud.order.biz.model.entity.purchase;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.web.base.entity.BaseSuperEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * 采购单子内容
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 09:02:26
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = PurchaseOrderItem.TABLE_NAME)
@TableName(PurchaseOrderItem.TABLE_NAME)
// @org.hibernate.annotations.Table(appliesTo = PurchaseOrderItem.TABLE_NAME, comment = "采购单子内容表")
public class PurchaseOrderItem extends BaseSuperEntity<PurchaseOrderItem, Long> {

    public static final String TABLE_NAME = "tt_purchase_order_item";

    /** 采购ID */
    @Column(name = "purchase_order_id", columnDefinition = "bigint not null comment '采购ID'")
    private Long purchaseOrderId;
    /** 商品名称 */
    @Column(name = "goods_name", columnDefinition = "varchar(255) not null comment '商品名称'")
    private String goodsName;
    /** 数量 */
    @Column(name = "num", columnDefinition = "int not null comment '数量'")
    private Integer num;
    /** 数量单位 */
    @Column(name = "goods_unit", columnDefinition = "varchar(255) not null comment '数量单位'")
    private String goodsUnit;
    /** 价格 */
    @Column(name = "price", columnDefinition = "decimal(10,2) not null comment '价格'")
    private BigDecimal price;
    /** 规格 */
    @Column(name = "specs", columnDefinition = "varchar(255) not null comment '规格'")
    private String specs;
    /** 图片 */
    @Column(name = "images", columnDefinition = "text not null comment '图片'")
    private String images;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem) o;
        return getId() != null && Objects.equals(getId(), purchaseOrderItem.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
