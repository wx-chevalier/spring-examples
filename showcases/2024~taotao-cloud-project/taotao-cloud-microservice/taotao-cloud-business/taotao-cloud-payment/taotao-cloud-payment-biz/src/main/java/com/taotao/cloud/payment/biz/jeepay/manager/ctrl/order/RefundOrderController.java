/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.payment.biz.jeepay.manager.ctrl.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeequan.jeepay.core.constants.ApiCodeEnum;
import com.jeequan.jeepay.core.entity.RefundOrder;
import com.jeequan.jeepay.core.model.ApiPageRes;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.service.impl.RefundOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款订单类
 *
 * @author pangxiaoyu
 * @site https://www.jeequan.com
 * @since 2021-06-07 07:15
 */
@Api(tags = "订单管理（退款类）")
@RestController
@RequestMapping("/api/refundOrder")
public class RefundOrderController extends CommonCtrl {

    @Autowired private RefundOrderService refundOrderService;

    /**
     * @author: pangxiaoyu
     * @since: 2021/6/7 16:15
     * @describe: 退款订单信息列表
     */
    @ApiOperation("退款订单信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iToken", value = "用户身份凭证", required = true, paramType = "header"),
            @ApiImplicitParam(name = "pageNumber", value = "分页页码", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页条数", dataType = "int", defaultValue = "20"),
            @ApiImplicitParam(name = "createdStart", value = "日期格式字符串（yyyy-MM-dd HH:mm:ss），时间范围查询--开始时间，查询范围：大于等于此时间"),
            @ApiImplicitParam(name = "createdEnd", value = "日期格式字符串（yyyy-MM-dd HH:mm:ss），时间范围查询--结束时间，查询范围：小于等于此时间"),
            @ApiImplicitParam(name = "mchNo", value = "商户号"),
            @ApiImplicitParam(name = "unionOrderId", value = "支付/退款订单号"),
            @ApiImplicitParam(name = "isvNo", value = "服务商号"),
            @ApiImplicitParam(name = "appId", value = "应用ID"),
            @ApiImplicitParam(name = "state", value = "退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-退款任务关闭", dataType = "Byte"),
            @ApiImplicitParam(name = "mchType", value = "类型: 1-普通商户, 2-特约商户(服务商模式)")
    })
    @PreAuthorize("hasAuthority('ENT_REFUND_LIST')")
    @RequestMapping(value="", method = RequestMethod.GET)
    public ApiPageRes<RefundOrder> list() {

        RefundOrder refundOrder = getObject(RefundOrder.class);
        JSONObject paramJSON = getReqParamJSON();
        LambdaQueryWrapper<RefundOrder> wrapper = RefundOrder.gw();
        IPage<RefundOrder> pages = refundOrderService.pageList(getIPage(), wrapper, refundOrder, paramJSON);

        return ApiPageRes.pages(pages);
    }

    /**
     * @author: pangxiaoyu
     * @since: 2021/6/7 16:15
     * @describe: 退款订单信息
     */
    @ApiOperation("退款订单信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "iToken", value = "用户身份凭证", required = true, paramType = "header"),
            @ApiImplicitParam(name = "refundOrderId", value = "退款订单号", required = true)
    })
    @PreAuthorize("hasAuthority('ENT_REFUND_ORDER_VIEW')")
    @RequestMapping(value="/{refundOrderId}", method = RequestMethod.GET)
    public ApiRes detail(@PathVariable("refundOrderId") String refundOrderId) {
        RefundOrder refundOrder = refundOrderService.getById(refundOrderId);
        if (refundOrder == null) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_SELETE);
        }
        return ApiRes.ok(refundOrder);
    }
}
