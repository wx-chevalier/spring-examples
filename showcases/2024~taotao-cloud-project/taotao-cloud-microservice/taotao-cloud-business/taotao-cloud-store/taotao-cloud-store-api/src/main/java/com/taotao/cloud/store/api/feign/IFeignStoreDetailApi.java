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

package com.taotao.cloud.store.api.feign;

import com.taotao.cloud.common.constant.ServiceName;
import com.taotao.cloud.store.api.feign.fallback.FeignStoreApiFallback;
import com.taotao.cloud.store.api.model.vo.StoreAfterSaleAddressVO;
import com.taotao.cloud.store.api.model.vo.StoreBasicInfoVO;
import com.taotao.cloud.store.api.model.vo.StoreDetailInfoVO;
import com.taotao.cloud.store.api.model.vo.StoreDetailVO;
import com.taotao.cloud.store.api.model.vo.StoreOtherVO;
import com.taotao.cloud.store.api.model.vo.StoreVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用店铺模块
 *
 * @author shuigedeng
 * @since 2020/5/2 16:42
 */
@FeignClient(
        contextId = "IFeignStoreDetailService",
        value = ServiceName.TAOTAO_CLOUD_GOODS,
        fallbackFactory = FeignStoreApiFallback.class)
public interface IFeignStoreDetailApi {

    @GetMapping(value = "/sotre/info/id/{id:[0-9]*}")
    StoreVO findSotreById(@RequestParam String id);

    @GetMapping(value = "/sotre/info/id/{id:[0-9]*}")
    StoreAfterSaleAddressVO getStoreAfterSaleAddressDTO(@RequestParam Long storeId);

    @GetMapping(value = "/get/detail/{storeId}")
    StoreDetailInfoVO getStoreDetailVO(@PathVariable Long storeId);

    @GetMapping(value = "/getStoreBasicInfoDTO")
    StoreBasicInfoVO getStoreBasicInfoDTO(String id);

    @GetMapping(value = "/getStoreOtherVO")
    StoreOtherVO getStoreOtherVO(String id);

    @GetMapping(value = "/getStoreDetailByMemberId")
    StoreDetailVO getStoreDetailByMemberId(Long userId);
}
