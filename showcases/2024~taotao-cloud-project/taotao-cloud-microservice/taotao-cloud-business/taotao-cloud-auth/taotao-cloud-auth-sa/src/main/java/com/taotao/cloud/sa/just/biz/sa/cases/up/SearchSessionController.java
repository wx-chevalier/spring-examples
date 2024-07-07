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

package com.taotao.cloud.sa.just.biz.sa.cases.up;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.taotao.cloud.security.satoken.model.SysUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sa-Token 会话查询示例
 *
 * @author kong
 * @since 2022-10-17
 */
@RestController
@RequestMapping("/search/")
public class SearchSessionController {

    /*
    * 测试步骤：
    	1、先登录5个账号
    		 ---- http://localhost:8081/search/login?userId=10001&张三&age=18
    		 ---- http://localhost:8081/search/login?userId=10002&李四&age=20
    		 ---- http://localhost:8081/search/login?userId=10003&王五&age=22
    		 ---- http://localhost:8081/search/login?userId=10004&赵六&age=24
    		 ---- http://localhost:8081/search/login?userId=10005&冯七&age=26

    	2、根据分页参数获取会话列表
    		http://localhost:8081/search/getList?start=0&size=10
    */

    // 会话登录接口  ---- http://localhost:8081/search/login?userId=10001&张三&age=18
    @RequestMapping("login")
    public SaResult login(long userId, String name, int age) {
        // 先登录上
        StpUtil.login(userId);

        // 再把 User 对象存储在 SaSession 上
        SysUser user = new SysUser();
        user.setId(userId);
        user.setName(name);
        user.setAge(age);
        StpUtil.getSession().set("user", user);

        // 返回
        return SaResult.ok("账号登录成功");
    }

    // 会话查询接口  ---- http://localhost:8081/search/getList?start=0&size=10
    @RequestMapping("getList")
    public SaResult getList(int start, int size) {
        // 创建集合
        List<SaSession> sessionList = new ArrayList<>();

        // 分页查询数据
        List<String> sessionIdList = StpUtil.searchSessionId("", start, size, false);
        for (String sessionId : sessionIdList) {
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            sessionList.add(session);
        }

        // 返回
        return SaResult.data(sessionList);
    }

    // 会话查询接口  ---- http://localhost:8081/disable/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok("账号退出成功");
    }
}
