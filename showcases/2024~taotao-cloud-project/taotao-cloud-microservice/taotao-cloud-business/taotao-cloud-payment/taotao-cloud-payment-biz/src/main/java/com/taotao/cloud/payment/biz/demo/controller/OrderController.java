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

package com.taotao.cloud.payment.biz.demo.controller;

import org.dromara.hutoolcore.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yungouos.springboot.demo.common.ApiResponse;
import com.yungouos.springboot.demo.entity.Order;
import com.yungouos.springboot.demo.service.order.OrderService;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @RequestMapping("/checkOrderStatus")
    @ResponseBody
    public JSONObject checkOrderStatus(@RequestParam Map<String, String> data) {
        JSONObject response = ApiResponse.init();
        try {
            String orderNo = data.get("orderNo");
            if (StrUtil.isBlank(orderNo)) {
                response = ApiResponse.fail("订单号不能为空");
                return response;
            }
            Order order = orderService.getOrderInfo(orderNo);

            if (order.getStatus().intValue() == 0) {
                response = ApiResponse.success("查询成功", false);
                return response;
            }
            response = ApiResponse.success("查询成功", true);
        } catch (Exception e) {
            LogUtils.error(e);
        }
        return response;
    }
}
