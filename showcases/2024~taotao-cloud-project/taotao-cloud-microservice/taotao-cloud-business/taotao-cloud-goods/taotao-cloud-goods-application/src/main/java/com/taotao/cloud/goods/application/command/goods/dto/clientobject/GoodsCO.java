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

package com.taotao.cloud.goods.application.command.goods.dto.clientobject;

import com.taotao.cloud.goods.api.enums.GoodsTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 商品基础VO
 *
 * @author shuigedeng
 * @version 2021.10
 * @since 2022-03-11 17:21:04
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商品基础VO")
public class GoodsCO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1450550797436233753L;

    private Long id;

    @Schema(description = "商品名称")
    private String goodsName;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "品牌id")
    private String brandId;

    @Schema(description = "分类path")
    private String categoryPath;

    @Schema(description = "计量单位")
    private String goodsUnit;

    @Schema(description = "商品卖点太长")
    private String sellingPoint;

    @Schema(description = "上架状态")
    private String marketEnable;

    @Schema(description = "详情")
    private String intro;

    @Schema(description = "购买数量")
    private Integer buyCount;

    @Schema(description = "库存")
    private Integer quantity;

    @Schema(description = "商品好评率")
    private BigDecimal grade;

    @Schema(description = "缩略图路径")
    private String thumbnail;

    @Schema(description = "小图路径")
    private String small;

    @Schema(description = "原图路径")
    private String original;

    @Schema(description = "店铺分类id")
    private String storeCategoryPath;

    @Schema(description = "评论数量")
    private Integer commentNum;

    @Schema(description = "卖家id")
    private String storeId;

    @Schema(description = "卖家名字")
    private String storeName;

    @Schema(description = "运费模板id")
    private String templateId;

    @Schema(description = "审核状态")
    private String isAuth;

    @Schema(description = "审核信息")
    private String authMessage;

    @Schema(description = "下架原因")
    private String underMessage;

    @Schema(description = "是否自营")
    private Boolean selfOperated;

    @Schema(description = "商品移动端详情")
    private String mobileIntro;

    @Schema(description = "商品视频")
    private String goodsVideo;

    @Schema(description = "是否为推荐商品")
    private Boolean recommend;

    @Schema(description = "销售模式")
    private String salesModel;

    @Schema(description = "商品海报id")
    private Long posterPicId;

    /**
     * @see GoodsTypeEnum
     */
    @Schema(description = "商品类型")
    private String goodsType;

    @Schema(description = "商品参数json")
    private String params;

    private Boolean delFlag;
}
