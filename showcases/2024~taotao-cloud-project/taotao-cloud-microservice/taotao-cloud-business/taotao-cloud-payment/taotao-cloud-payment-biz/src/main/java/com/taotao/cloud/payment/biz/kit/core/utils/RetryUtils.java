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

package com.taotao.cloud.payment.biz.kit.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IJPay 让支付触手可及，封装了微信支付、支付宝支付、银联支付常用的支付方式以及各种常用的接口。
 *
 * <p>不依赖任何第三方 mvc 框架，仅仅作为工具使用简单快速完成支付模块的开发，可轻松嵌入到任何系统里。
 *
 * <p>IJPay 交流群: 723992875
 *
 * <p>Node.js 版: https://gitee.com/javen205/TNWX
 *
 * <p>异常重试工具 by L.cm
 *
 * @author Javen
 */
public class RetryUtils {
    private static final Logger log = LoggerFactory.getLogger(RetryUtils.class);

    /** 回调结果检查 */
    public interface ResultCheck {
        /**
         * 是否匹配
         *
         * @return 匹配结果
         */
        boolean matching();

        /**
         * 获取 JSON
         *
         * @return json
         */
        String getJson();
    }

    /**
     * 在遇到异常时尝试重试
     *
     * @param retryLimit 重试次数
     * @param retryCallable 重试回调
     * @param <V> 泛型
     * @return V 结果
     */
    public static <V extends ResultCheck> V retryOnException(
            int retryLimit, java.util.concurrent.Callable<V> retryCallable) {
        V v = null;
        for (int i = 0; i < retryLimit; i++) {
            try {
                v = retryCallable.call();
            } catch (Exception e) {
                log.warn("retry on " + (i + 1) + " times v = " + (v == null ? null : v.getJson()), e);
            }
            if (null != v && v.matching()) break;
            log.error("retry on " + (i + 1) + " times but not matching v = " + (v == null ? null : v.getJson()));
        }
        return v;
    }

    /**
     * 在遇到异常时尝试重试
     *
     * @param retryLimit 重试次数
     * @param sleepMillis 每次重试之后休眠的时间
     * @param retryCallable 重试回调
     * @param <V> 泛型
     * @return V 结果
     * @throws InterruptedException 线程异常
     */
    public static <V extends ResultCheck> V retryOnException(
            int retryLimit, long sleepMillis, java.util.concurrent.Callable<V> retryCallable)
            throws InterruptedException {
        V v = null;
        for (int i = 0; i < retryLimit; i++) {
            try {
                v = retryCallable.call();
            } catch (Exception e) {
                log.warn("retry on " + (i + 1) + " times v = " + (v == null ? null : v.getJson()), e);
            }
            if (null != v && v.matching()) {
                break;
            }
            log.error("retry on " + (i + 1) + " times but not matching v = " + (v == null ? null : v.getJson()));
            if (sleepMillis > 0) {
                Thread.sleep(sleepMillis);
            }
        }
        return v;
    }
}
