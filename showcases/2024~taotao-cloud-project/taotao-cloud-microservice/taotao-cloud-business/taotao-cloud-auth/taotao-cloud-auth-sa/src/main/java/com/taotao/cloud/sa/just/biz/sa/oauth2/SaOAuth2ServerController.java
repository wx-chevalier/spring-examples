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

package com.taotao.cloud.sa.just.biz.sa.oauth2;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Sa-OAuth2 Server端 控制器
 *
 * @author kong
 */
@RestController
public class SaOAuth2ServerController {

    // 处理所有OAuth相关请求
    @RequestMapping("/oauth2/*")
    public Object request() {
        LogUtils.info("------- 进入请求: " + SaHolder.getRequest().getUrl());
        return SaOAuth2Handle.serverRequest();
    }

    // Sa-OAuth2 定制化配置
    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        cfg.
                // 未登录的视图
                setNotLoginView(() -> {
                    return new ModelAndView("login.html");
                })
                .
                // 登录处理函数
                setDoLoginHandle((name, pwd) -> {
                    if ("sa".equals(name) && "123456".equals(pwd)) {
                        StpUtil.login(10001);
                        return SaResult.ok();
                    }
                    return SaResult.error("账号名或密码错误");
                })
                .
                // 授权确认视图
                setConfirmView((clientId, scope) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("clientId", clientId);
                    map.put("scope", scope);
                    return new ModelAndView("confirm.html", map);
                });
    }

    // 全局异常拦截
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        LogUtils.error(e);
        return SaResult.error(e.getMessage());
    }

    // ---------- 开放相关资源接口： Client端根据 Access-Token ，置换相关资源 ------------

    // 获取Userinfo信息：昵称、头像、性别等等
    @RequestMapping("/oauth2/userinfo")
    public SaResult userinfo() {
        // 获取 Access-Token 对应的账号id
        String accessToken = SaHolder.getRequest().getParamNotNull("access_token");
        Object loginId = SaOAuth2Util.getLoginIdByAccessToken(accessToken);
        LogUtils.info("-------- 此Access-Token对应的账号id: " + loginId);

        // 校验 Access-Token 是否具有权限: userinfo
        SaOAuth2Util.checkScope(accessToken, "userinfo");

        // 模拟账号信息 （真实环境需要查询数据库获取信息）
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("nickname", "shengzhang_");
        map.put("avatar", "http://xxx.com/1.jpg");
        map.put("age", "18");
        map.put("sex", "男");
        map.put("address", "山东省 青岛市 城阳区");
        return SaResult.data(map);
    }
}
