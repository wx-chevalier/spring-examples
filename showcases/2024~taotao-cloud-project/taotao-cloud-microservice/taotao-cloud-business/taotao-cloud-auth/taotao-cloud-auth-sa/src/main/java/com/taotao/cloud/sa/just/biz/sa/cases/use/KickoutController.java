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

package com.taotao.cloud.sa.just.biz.sa.cases.use;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token 权限认证示例
 *
 * @author kong
 * @since 2022-10-13
 */
@RestController
@RequestMapping("/kickout/")
public class KickoutController {

    /*
     * 前提：首先调用登录接口进行登录，代码在 com.pj.cases.use.LoginAuthController 中有详细解释，此处不再赘述
     * 		---- http://localhost:8081/acc/doLogin?name=zhang&pwd=123456
     */

    // 将指定账号强制注销   ---- http://localhost:8081/kickout/logout?userId=10001
    @RequestMapping("logout")
    public SaResult logout(long userId) {

        // 强制注销等价于对方主动调用了注销方法，再次访问会提示：Token无效。
        StpUtil.logout(userId);

        // 返回
        return SaResult.ok();
    }

    // 将指定账号踢下线   ---- http://localhost:8081/kickout/kickout?userId=10001
    @RequestMapping("kickout")
    public SaResult kickout(long userId) {

        // 踢人下线不会清除Token信息，而是将其打上特定标记，再次访问会提示：Token已被踢下线。
        StpUtil.kickout(userId);

        // 返回
        return SaResult.ok();
    }

    /*
     * 你可以分别在强制注销和踢人下线后，再次访问一下登录校验接口，对比一下两者返回的提示信息有何不同
     * 		---- http://localhost:8081/acc/checkLogin
     */

    // 根据 Token 值踢人    ----
    // http://localhost:8081/kickout/kickoutByTokenValue?tokenValue=xxxx-xxxx-xxxx-xxxx已登录账号的token值
    @RequestMapping("kickoutByTokenValue")
    public SaResult kickoutByTokenValue(String tokenValue) {

        StpUtil.kickoutByTokenValue(tokenValue);

        // 返回
        return SaResult.ok();
    }
}
