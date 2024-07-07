package com.taotao.cloud.payment.biz.daxpay.single.core.code;

import cn.bootx.platform.daxpay.exception.pay.PayFailureException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 支付方式
 *
 * @author xxm
 * @since 2021/7/26
 */
@Getter
@AllArgsConstructor
public enum PayWayEnum {

    NORMAL("normal", "常规支付"),
    WAP("wap", "wap支付"),
    APP("app", "应用支付"),
    WEB("web", "web支付"),
    QRCODE("qrcode", "扫码支付"),
    BARCODE("barcode", "付款码"),
    // 通用
    JSAPI("jsapi", "JSAPI方式"),
    B2B("b2b", "企业网银");

    /** 编码 */
    private final String code;

    /** 名称 */
    private final String name;

    /**
     * 根据字符编码获取
     */
    public static PayWayEnum findByCode(String code) {
        return Arrays.stream(PayWayEnum.values())
            .filter(e -> Objects.equals(code, e.getCode()))
            .findFirst()
            .orElseThrow(() -> new PayFailureException("不存在的支付方式"));
    }

}
