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

package com.taotao.cloud.payment.biz.kit.plugin.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.taotao.cloud.common.utils.log.LogUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** 支付宝支付 */
public class AliPayRequest {

    /**
     * WAP支付
     *
     * @param response {@link HttpServletResponse}
     * @param model {@link AlipayTradeWapPayModel}
     * @param returnUrl 异步通知URL
     * @param notifyUrl 同步通知URL
     * @throws AlipayApiException 支付宝 Api 异常
     * @throws IOException IO 异常
     */
    public static void wapPay(
            HttpServletResponse response, AlipayTradeWapPayModel model, String returnUrl, String notifyUrl)
            throws AlipayApiException, IOException {
        String form = wapPayStr(model, returnUrl, notifyUrl);
        response.setContentType("text/html;charset=UTF-8");
        LogUtils.info("支付表单{}", form);
        PrintWriter out = response.getWriter();
        out.write(form);
        out.flush();
        out.close();
    }

    /**
     * APP支付
     *
     * @param model {@link AlipayTradeAppPayModel}
     * @param notifyUrl 异步通知 URL
     * @return {@link AlipayTradeAppPayResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayTradeAppPayResponse appPayToResponse(AlipayTradeAppPayModel model, String notifyUrl)
            throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        return sdkExecute(request);
    }

    /**
     * 电脑网站支付(PC支付)
     *
     * @param response {@link HttpServletResponse}
     * @param model {@link AlipayTradePagePayModel}
     * @param notifyUrl 异步通知URL
     * @param returnUrl 同步通知URL
     * @throws AlipayApiException 支付宝 Api 异常
     * @throws IOException IO 异常
     */
    public static void tradePage(
            HttpServletResponse response, AlipayTradePagePayModel model, String notifyUrl, String returnUrl)
            throws AlipayApiException, IOException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);
        String form = pageExecute(request).getBody();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(form);
        out.flush();
        out.close();
    }

    /**
     * 统一收单线下交易预创建 <br>
     * 适用于：扫码支付等 <br>
     *
     * @param model {@link AlipayTradePrecreateModel}
     * @param notifyUrl 异步通知URL
     * @return {@link AlipayTradePrecreateResponse}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static AlipayTradePrecreateResponse tradePrecreatePayToResponse(
            AlipayTradePrecreateModel model, String notifyUrl) throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        return doExecute(request);
    }

    /**
     * WAP支付
     *
     * @param model {@link AlipayTradeWapPayModel}
     * @param returnUrl 异步通知URL
     * @param notifyUrl 同步通知URL
     * @return {String}
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public static String wapPayStr(AlipayTradeWapPayModel model, String returnUrl, String notifyUrl)
            throws AlipayApiException {
        AlipayTradeWapPayRequest aliPayRequest = new AlipayTradeWapPayRequest();
        aliPayRequest.setReturnUrl(returnUrl);
        aliPayRequest.setNotifyUrl(notifyUrl);
        aliPayRequest.setBizModel(model);
        return pageExecute(aliPayRequest).getBody();
    }

    public static <T extends AlipayResponse> T doExecute(AlipayRequest<T> request) throws AlipayApiException {
        return certificateExecute(request);
    }

    public static <T extends AlipayResponse> T certificateExecute(AlipayRequest<T> request) throws AlipayApiException {
        return AliPayApiConfigKit.getAliPayApiConfig().certificateExecute(request);
    }

    public static <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request) throws AlipayApiException {
        return AliPayApiConfigKit.getAliPayApiConfig().pageExecute(request);
    }

    public static <T extends AlipayResponse> T sdkExecute(AlipayRequest<T> request) throws AlipayApiException {
        return AliPayApiConfigKit.getAliPayApiConfig().sdkExecute(request);
    }
}
