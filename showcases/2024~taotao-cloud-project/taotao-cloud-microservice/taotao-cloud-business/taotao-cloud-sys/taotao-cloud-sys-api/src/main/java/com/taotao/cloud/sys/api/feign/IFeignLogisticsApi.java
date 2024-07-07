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

package com.taotao.cloud.sys.api.feign;

import com.taotao.cloud.common.constant.ServiceName;
import com.taotao.cloud.common.model.Result;
import com.taotao.cloud.sys.api.feign.fallback.FeignLogisticsApiFallback;
import com.taotao.cloud.sys.api.feign.response.FeignLogisticsResponse;
import com.taotao.cloud.sys.api.feign.response.FeignTracesResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用物流公司模块
 *
 * @author shuigedeng
 * @version 2022.03
 * @since 2022-03-25 14:09:10
 */
@FeignClient(
	name = ServiceName.TAOTAO_CLOUD_SYS,
	contextId = "IFeignLogisticsApi",
	fallbackFactory = FeignLogisticsApiFallback.class)
public interface IFeignLogisticsApi {

	/**
	 * 通过id
	 *
	 * @param logisticsId 物流id
	 * @return {@link Result }<{@link FeignLogisticsResponse }>
	 * @since 2022-04-25 16:47:29
	 */
	@GetMapping("/sys/feign/logistic/codes")
	FeignLogisticsResponse getById(@RequestParam Long logisticsId);

	/**
	 * 得到物流
	 *
	 * @param logisticsId 物流id
	 * @param logisticsNo 物流编码
	 * @return {@link Result }<{@link FeignTracesResponse }>
	 * @since 2022-04-25 16:47:32
	 */
	@GetMapping("/sys/feign/logistic/info")
	FeignTracesResponse getLogistic(@RequestParam Long logisticsId,
		@RequestParam String logisticsNo);

	@GetMapping("/sys/feign/logistic/list")
	List<FeignLogisticsResponse> list();
}
