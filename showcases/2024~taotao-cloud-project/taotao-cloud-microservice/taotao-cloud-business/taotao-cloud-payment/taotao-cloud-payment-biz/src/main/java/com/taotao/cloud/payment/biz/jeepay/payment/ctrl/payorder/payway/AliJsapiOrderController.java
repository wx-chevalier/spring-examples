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
package com.taotao.cloud.payment.biz.jeepay.payment.ctrl.payorder.payway;

import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.pay.ctrl.payorder.AbstractPayOrderController;
import com.jeequan.jeepay.pay.rqrs.payorder.payway.AliJsapiOrderRQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 支付宝 jspai controller
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @since 2021/6/8 17:25
 */
@Slf4j
@RestController
public class AliJsapiOrderController extends AbstractPayOrderController {


    /**
     * 统一下单接口
     * **/
    @PostMapping("/api/pay/aliJsapiOrder")
    public ApiRes aliJsapiOrder(){

        //获取参数 & 验证
        AliJsapiOrderRQ bizRQ = getRQByWithMchSign(AliJsapiOrderRQ.class);

        // 统一下单接口
        return unifiedOrder(CS.PAY_WAY_CODE.ALI_JSAPI, bizRQ);

    }


}
