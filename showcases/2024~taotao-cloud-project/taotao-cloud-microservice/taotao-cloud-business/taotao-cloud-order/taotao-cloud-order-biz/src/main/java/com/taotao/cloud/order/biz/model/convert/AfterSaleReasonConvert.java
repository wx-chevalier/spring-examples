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

package com.taotao.cloud.order.biz.model.convert;

import com.taotao.cloud.order.sys.model.dto.aftersale.AfterSaleReasonDTO;
import com.taotao.cloud.order.sys.model.vo.aftersale.AfterSaleReasonVO;
import com.taotao.cloud.order.biz.model.entity.aftersale.AfterSaleReason;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * IAfterSaleMapStruct
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-07 20:55:46
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AfterSaleReasonConvert {

    AfterSaleReasonConvert INSTANCE = Mappers.getMapper(AfterSaleReasonConvert.class);

    List<AfterSaleReasonVO> convert(List<AfterSaleReason> afterSaleReasonList);

    AfterSaleReasonVO convert(AfterSaleReason afterSaleReason);

    AfterSaleReason convert(AfterSaleReasonDTO afterSaleReasonDTO);
}
