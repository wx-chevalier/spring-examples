/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opengoofy.congomall.biz.pay.domain.base;

import lombok.Data;
import org.opengoofy.congomall.biz.pay.domain.common.PayChannelEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付宝回调请求入参
 *
 * @author chen.ma
 * @github <a href="https://github.com/opengoofy" />
 * @公众号 马丁玩编程，关注回复：资料，领取后端技术专家成长手册
 */
@Data
public final class AliPayCallbackRequest extends AbstractPayCallbackRequest {
    
    /**
     * 支付渠道
     */
    private String channel;
    
    /**
     * 支付状态
     */
    private String tradeStatus;
    
    /**
     * 支付凭证号
     */
    private String tradeNo;
    
    /**
     * 买家付款时间
     */
    private Date gmtPayment;
    
    /**
     * 买家付款金额
     */
    private BigDecimal buyerPayAmount;
    
    @Override
    public AliPayCallbackRequest getAliPayCallBackRequest() {
        return this;
    }
    
    @Override
    public String buildMark() {
        return PayChannelEnum.ALI_PAY.name();
    }
}
