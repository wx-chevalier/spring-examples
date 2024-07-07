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
package com.taotao.cloud.payment.biz.jeepay.merchant.ctrl.paytest;

import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.entity.MchApp;
import com.jeequan.jeepay.mch.ctrl.CommonCtrl;
import com.jeequan.jeepay.mch.websocket.server.WsPayOrderServer;
import com.jeequan.jeepay.service.impl.MchAppService;
import com.jeequan.jeepay.util.JeepayKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/*
* 支付测试 - 回调函数
*
* @author terrfly
* @site https://www.jeequan.com
* @since 2021/6/22 14:22
*/
@Api(tags = "支付测试")
@RestController
@RequestMapping("/api/anon/paytestNotify")
public class PaytestNotifyController extends CommonCtrl {

    @Autowired private MchAppService mchAppService;

    @ApiOperation("支付回调信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", required = true),
            @ApiImplicitParam(name = "mchNo", value = "商户号", required = true),
            @ApiImplicitParam(name = "sign", value = "签名值", required = true)
    })
    @RequestMapping("/payOrder")
    public void payOrderNotify() throws IOException {

        //请求参数
        JSONObject params = getReqParamJSON();

        String mchNo = params.getString("mchNo");
        String appId = params.getString("appId");
        String sign = params.getString("sign");
        MchApp mchApp = mchAppService.getById(appId);
        if(mchApp == null || !mchApp.getMchNo().equals(mchNo)){
            response.getWriter().print("app is not exists");
            return;
        }

        params.remove("sign");
        if(!JeepayKit.getSign(params, mchApp.getAppSecret()).equalsIgnoreCase(sign)){
            response.getWriter().print("sign fail");
            return;
        }

        JSONObject msg = new JSONObject();
        msg.put("state", params.getIntValue("state"));
        msg.put("errCode", params.getString("errCode"));
        msg.put("errMsg", params.getString("errMsg"));

        //推送到前端
        WsPayOrderServer.sendMsgByOrderId(params.getString("payOrderId"), msg.toJSONString());

        response.getWriter().print("SUCCESS");
    }

}
