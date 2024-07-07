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

package com.taotao.cloud.goods.biz.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** 分类VO */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTreeVO extends CategoryVO {

    private static final long serialVersionUID = 3775766246075838410L;

    @Schema(description = "父节点名称")
    private String parentTitle;

    @Schema(description = "子分类列表")
    private List<CategoryTreeVO> children;

    @Schema(description = "分类关联的品牌列表")
    private List<BrandVO> brandList;

    public List<CategoryTreeVO> getChildren() {
        if (children != null) {
            children.sort(Comparator.comparing(CategoryVO::getSortOrder));
            return children;
        }
        return null;
    }
}
