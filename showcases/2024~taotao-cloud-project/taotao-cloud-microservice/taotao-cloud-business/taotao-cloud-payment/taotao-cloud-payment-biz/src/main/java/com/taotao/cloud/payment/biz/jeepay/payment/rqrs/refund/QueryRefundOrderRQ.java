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
package com.taotao.cloud.payment.biz.jeepay.payment.rqrs.refund;

import com.jeequan.jeepay.pay.rqrs.AbstractMchAppRQ;
import lombok.Data;

/*
* 查询退款单请求参数对象
*
* @author terrfly
* @site https://www.jeequan.com
* @since 2021/6/17 14:07
*/
@Data
public class QueryRefundOrderRQ extends AbstractMchAppRQ {

    /** 商户退款单号 **/
    private String mchRefundNo;

    /** 支付系统退款订单号 **/
    private String refundOrderId;

}
