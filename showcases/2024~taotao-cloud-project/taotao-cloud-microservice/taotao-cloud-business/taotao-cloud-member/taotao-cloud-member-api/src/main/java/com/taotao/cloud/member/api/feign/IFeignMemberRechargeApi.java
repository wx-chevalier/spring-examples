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

package com.taotao.cloud.member.api.feign;

import com.taotao.cloud.common.constant.ServiceName;
import com.taotao.cloud.member.api.feign.fallback.FeignMemberRechargeApiFallback;
import com.taotao.cloud.member.api.feign.response.FeignMemberRechargeResponse;
import java.math.BigDecimal;
import java.util.List;

import org.dromara.hutool.core.date.DateTime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用会员用户模块
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-25 16:37:54
 */
@FeignClient(value = ServiceName.TAOTAO_CLOUD_MEMBER, fallbackFactory = FeignMemberRechargeApiFallback.class)
public interface IFeignMemberRechargeApi {

    @GetMapping(value = "/member/feign/recharge/paySuccess")
    Boolean paySuccess(@RequestParam String sn, @RequestParam String receivableNo, @RequestParam String paymentMethod);

    @GetMapping(value = "/member/feign/recharge/getRecharge")
	FeignMemberRechargeResponse getRecharge(@RequestParam String sn);

    @GetMapping(value = "/member/feign/recharge/recharge")
	FeignMemberRechargeResponse recharge(@RequestParam BigDecimal price);

    /**
     * LambdaQueryWrapper<Recharge> queryWrapper = new LambdaQueryWrapper<>();
     * queryWrapper.eq(Recharge::getPayStatus, PayStatusEnum.UNPAID.name()); //充值订单创建时间 <= 订单自动取消时间
     * queryWrapper.le(Recharge::getCreateTime, cancelTime);
     *
     * @return
     */
    @GetMapping(value = "/member/feign/recharge/list")
    List<FeignMemberRechargeResponse> list(@RequestParam DateTime dateTime);

    @GetMapping(value = "/member/feign/recharge/rechargeOrderCancel")
    Boolean rechargeOrderCancel(@RequestParam String sn);
}
