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

package org.opengoofy.congomall.biz.pay.domain.common;

import cn.hutool.core.collection.ListUtil;
import org.opengoofy.congomall.springboot.starter.convention.exception.ServiceException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 交易状态枚举
 * 映射不同平台状态码，最终入库的值为枚举名
 *
 * @author chen.ma
 * @github <a href="https://github.com/opengoofy" />
 * @公众号 马丁玩编程，关注回复：资料，领取后端技术专家成长手册
 */
public enum TradeStatusEnum {
    
    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY {
        
        @Override
        protected List<String> tradeStatus() {
            return ListUtil.of("WAIT_BUYER_PAY");
        }
    },
    
    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    TRADE_CLOSED {
        
        @Override
        protected List<String> tradeStatus() {
            return ListUtil.of("TRADE_CLOSED");
        }
    },
    
    /**
     * 交易支付成功
     */
    TRADE_SUCCESS {
        
        @Override
        protected List<String> tradeStatus() {
            return ListUtil.of("TRADE_SUCCESS");
        }
    },
    
    /**
     * 交易结束，不可退款
     */
    TRADE_FINISHED {
        
        @Override
        protected List<String> tradeStatus() {
            return ListUtil.of("TRADE_FINISHED");
        }
    };
    
    /**
     * 获取交易状态集合
     */
    protected abstract List<String> tradeStatus();
    
    /**
     * 查询真实的交易状态
     *
     * @param tradeStatus 三方交易状态
     * @return 真实的交易状态，入库使用
     */
    public static String queryActualTradeStatus(String tradeStatus) {
        Optional<TradeStatusEnum> tradeStatusEnum = Arrays.stream(TradeStatusEnum.values()).filter(each -> each.tradeStatus().contains(tradeStatus)).findFirst();
        return tradeStatusEnum.orElseThrow(() -> new ServiceException("未找到支付状态")).toString();
    }
}
