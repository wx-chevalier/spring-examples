package com.taotao.cloud.payment.biz.daxpay.single.service.core.payment.assist.service;

import cn.bootx.platform.daxpay.param.assist.WxAccessTokenParam;
import cn.bootx.platform.daxpay.param.assist.WxAuthUrlParam;
import cn.bootx.platform.daxpay.result.assist.WxAccessTokenResult;
import cn.bootx.platform.daxpay.result.assist.WxAuthUrlResult;
import cn.bootx.platform.daxpay.service.core.channel.wechat.entity.WeChatPayConfig;
import cn.bootx.platform.daxpay.service.core.channel.wechat.service.WeChatPayConfigService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.stereotype.Service;

/**
 * 支付支撑服务类
 * @author xxm
 * @since 2024/2/10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UniPayAssistService {
    private final WeChatPayConfigService weChatPayConfigService;

    /**
     * 构建微信oauth2授权的url连接
     */
    public WxAuthUrlResult getWxAuthUrl(WxAuthUrlParam param) {
        WxMpService wxMpService = this.getWxMpService();
        String url = wxMpService.getOAuth2Service()
                .buildAuthorizationUrl(param.getUrl(), WxConsts.OAuth2Scope.SNSAPI_BASE, param.getState());
        return new WxAuthUrlResult().setUrl(url);
    }

    /**
     * 获取微信AccessToken数据
     */
    @SneakyThrows
    public WxAccessTokenResult getWxAccessToken(WxAccessTokenParam wxAccessToken){
        WxMpService wxMpService = this.getWxMpService();
        WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(wxAccessToken.getCode());
        return new WxAccessTokenResult()
                .setAccessToken(accessToken.getAccessToken())
                .setOpenId(accessToken.getOpenId());
    }

    /**
     * 获取微信公众号API的Service
     */
    private WxMpService getWxMpService() {
        WeChatPayConfig config = weChatPayConfigService.getConfig();
        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl wxMpConfig = new WxMpDefaultConfigImpl();
        wxMpConfig.setAppId(config.getWxAppId()); // 设置微信公众号的appid
        wxMpConfig.setSecret(config.getAppSecret()); // 设置微信公众号的app corpSecret
        wxMpService.setWxMpConfigStorage(wxMpConfig);
        return wxMpService;
    }
}
