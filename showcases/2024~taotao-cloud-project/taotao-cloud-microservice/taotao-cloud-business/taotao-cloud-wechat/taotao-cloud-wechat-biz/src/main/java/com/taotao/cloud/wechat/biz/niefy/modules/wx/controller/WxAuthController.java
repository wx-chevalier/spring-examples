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

package com.taotao.cloud.wechat.biz.niefy.modules.wx.controller;

import com.github.niefy.common.utils.*;
import com.github.niefy.modules.sys.service.SysLogService;
import com.github.niefy.modules.wx.entity.WxUser;
import com.github.niefy.modules.wx.form.WxH5OuthrizeForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/** 微信网页授权相关 */
@RestController
@RequestMapping("/wxAuth")
@Api(tags = {"微信网页授权"})
@RequiredArgsConstructor
public class WxAuthController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysLogService sysLogService;

    private final WxMpService wxMpService;

    /**
     * 使用微信授权code换取openid
     *
     * @param request
     * @param response
     * @param form
     * @return
     */
    @PostMapping("/codeToOpenid")
    @CrossOrigin
    @ApiOperation(value = "网页登录-code换取openid", notes = "scope为snsapi_base")
    public R codeToOpenid(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue String appid,
            @RequestBody WxH5OuthrizeForm form) {
        try {
            this.wxMpService.switchoverTo(appid);
            WxOAuth2AccessToken token = wxMpService.getOAuth2Service().getAccessToken(form.getCode());
            String openid = token.getOpenId();
            CookieUtil.setCookie(response, "openid", openid, 365 * 24 * 60 * 60);
            String openidToken = MD5Util.getMd5AndSalt(openid);
            CookieUtil.setCookie(response, "openidToken", openidToken, 365 * 24 * 60 * 60);
            return R.ok().put(openid);
        } catch (WxErrorException e) {
            logger.error("code换取openid失败", e);
            return R.error(e.getError().getErrorMsg());
        }
    }

    /**
     * 使用微信授权code换取用户信息(需scope为 snsapi_userinfo)
     *
     * @param request
     * @param response
     * @param form
     * @return
     */
    @PostMapping("/codeToUserInfo")
    @CrossOrigin
    @ApiOperation(value = "网页登录-code换取用户信息", notes = "需scope为 snsapi_userinfo")
    public R codeToUserInfo(
            HttpServletRequest request,
            HttpServletResponse response,
            @CookieValue String appid,
            @RequestBody WxH5OuthrizeForm form) {
        try {
            this.wxMpService.switchoverTo(appid);
            WxOAuth2AccessToken token = wxMpService.getOAuth2Service().getAccessToken(form.getCode());
            WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(token, "zh_CN");
            String openid = userInfo.getOpenid();
            CookieUtil.setCookie(response, "openid", openid, 365 * 24 * 60 * 60);
            String openidToken = MD5Util.getMd5AndSalt(openid);
            CookieUtil.setCookie(response, "openidToken", openidToken, 365 * 24 * 60 * 60);
            return R.ok().put(new WxUser(userInfo, appid));
        } catch (WxErrorException e) {
            logger.error("code换取用户信息失败", e);
            return R.error(e.getError().getErrorMsg());
        }
    }

    /**
     * 获取微信分享的签名配置 允许跨域（只有微信公众号添加了js安全域名的网站才能加载微信分享，故这里不对域名进行校验）
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/getShareSignature")
    @ApiOperation(value = "获取微信分享的签名配置", notes = "微信公众号添加了js安全域名的网站才能加载微信分享")
    public R getShareSignature(HttpServletRequest request, HttpServletResponse response, @CookieValue String appid)
            throws WxErrorException {
        this.wxMpService.switchoverTo(appid);
        // 1.拼接url（当前网页的URL，不包含#及其后面部分）
        String wxShareUrl = request.getHeader(Constant.WX_CLIENT_HREF_HEADER);
        if (!StringUtils.hasText(wxShareUrl)) {
            return R.error("header中缺少" + Constant.WX_CLIENT_HREF_HEADER + "参数，微信分享加载失败");
        }
        wxShareUrl = wxShareUrl.split("#")[0];
        Map<String, String> wxMap = new TreeMap<>();
        String wxNoncestr = UUID.randomUUID().toString();
        String wxTimestamp = (System.currentTimeMillis() / 1000) + "";
        wxMap.put("noncestr", wxNoncestr);
        wxMap.put("timestamp", wxTimestamp);
        wxMap.put("jsapi_ticket", wxMpService.getJsapiTicket());
        wxMap.put("url", wxShareUrl);

        // 加密获取signature
        StringBuilder wxBaseString = new StringBuilder();
        wxMap.forEach((key, value) ->
                wxBaseString.append(key).append("=").append(value).append("&"));
        String wxSignString = wxBaseString.substring(0, wxBaseString.length() - 1);
        // signature
        String wxSignature = SHA1Util.sha1(wxSignString);
        Map<String, String> resMap = new TreeMap<>();
        resMap.put("appId", appid);
        resMap.put("wxTimestamp", wxTimestamp);
        resMap.put("wxNoncestr", wxNoncestr);
        resMap.put("wxSignature", wxSignature);
        return R.ok().put(resMap);
    }
}
