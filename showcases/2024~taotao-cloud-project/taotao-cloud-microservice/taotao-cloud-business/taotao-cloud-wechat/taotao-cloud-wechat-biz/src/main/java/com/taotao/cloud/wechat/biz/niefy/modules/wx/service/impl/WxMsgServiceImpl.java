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

package com.taotao.cloud.wechat.biz.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.WxMsgMapper;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.WxMsgService;
import java.util.Arrays;
import java.util.Map;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("wxMsgService")
public class WxMsgServiceImpl extends ServiceImpl<WxMsgMapper, WxMsg> implements WxMsgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String msgTypes = (String) params.get("msgTypes");
        String startTime = (String) params.get("startTime");
        String openid = (String) params.get("openid");
        String appid = (String) params.get("appid");
        IPage<WxMsg> page = this.page(
                new Query<WxMsg>().getPage(params),
                new QueryWrapper<WxMsg>()
                        .eq(StringUtils.hasText(appid), "appid", appid)
                        .in(StringUtils.hasText(msgTypes), "msg_type", Arrays.asList(msgTypes.split(",")))
                        .eq(StringUtils.hasText(openid), "openid", openid)
                        .ge(StringUtils.hasText(startTime), "create_time", startTime));

        return new PageUtils(page);
    }

    /**
     * 记录msg，异步入库
     *
     * @param msg
     */
    @Override
    @Async
    public void addWxMsg(WxMsg msg) {
        this.baseMapper.insert(msg);
    }
}
