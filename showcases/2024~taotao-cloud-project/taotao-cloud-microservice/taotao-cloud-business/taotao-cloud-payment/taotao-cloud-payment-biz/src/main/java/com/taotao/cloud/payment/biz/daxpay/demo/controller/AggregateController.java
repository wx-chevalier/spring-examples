package com.taotao.cloud.payment.biz.daxpay.demo.controller;

import cn.bootx.platform.common.core.annotation.IgnoreAuth;
import cn.bootx.platform.common.core.rest.Res;
import cn.bootx.platform.common.core.rest.ResResult;
import cn.bootx.platform.daxpay.demo.domain.AggregatePayInfo;
import cn.bootx.platform.daxpay.demo.param.AggregateSimplePayParam;
import cn.bootx.platform.daxpay.demo.result.PayOrderResult;
import cn.bootx.platform.daxpay.demo.result.WxJsapiSignResult;
import cn.bootx.platform.daxpay.demo.service.AggregateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpHeaders.USER_AGENT;

/**
 * 聚合支付
 * @author xxm
 * @since 2024/2/9
 */
@IgnoreAuth
@Tag(name = "聚合支付")
@RestController
@RequestMapping("/demo/aggregate")
@RequiredArgsConstructor
public class AggregateController {
    private final AggregateService aggregateService;

    @Operation(summary = "创建聚合支付码")
    @PostMapping("/createUrl")
    public ResResult<String> createUrl(@RequestBody AggregateSimplePayParam param) {
        return Res.ok(aggregateService.createUrl(param));
    }

    @Operation(summary = "获取聚合支付信息")
    @GetMapping("/getInfo")
    public ResResult<AggregatePayInfo> getInfo(String code){
        return Res.ok(aggregateService.getInfo(code));
    }

    @Operation(summary = "聚合支付扫码跳转中间页")
    @GetMapping("/qrPayPage/{code}")
    public ModelAndView qrPayPage(@PathVariable String code,@RequestHeader(USER_AGENT) String ua){
        String url = aggregateService.qrPayPage(code, ua);
        return new ModelAndView("redirect:" + url);
    }

    @Operation(summary = "支付宝通过聚合支付码发起支付")
    @PostMapping("/aliH5Pay")
    public ResResult<PayOrderResult> aliH5Pay(String code){
        return Res.ok(aggregateService.aliH5Pay(code));
    }

    @Operation(summary = "微信授权回调页面")
    @GetMapping("/wxAuthCallback")
    public ModelAndView wxAuthCallback(@RequestParam("state") String aggregateCode, @RequestParam("code") String authCode){
        return new ModelAndView("redirect:" + aggregateService.wxAuthCallback(aggregateCode, authCode));
    }

    @Operation(summary = "获取微信支付调起Jsapi支付的信息")
    @PostMapping("/getWxJsapiPay")
    public ResResult<WxJsapiSignResult> getWxJsapiPay(String aggregateCode, String openId){
        return Res.ok(aggregateService.wxJsapiPrePay(aggregateCode, openId));
    }

    @Operation(summary = "通过付款码发起支付")
    @PostMapping("/barCodePay")
    public ResResult<PayOrderResult> barCodePay(@RequestBody AggregateSimplePayParam param){
        return Res.ok(aggregateService.barPay(param));
    }

}
