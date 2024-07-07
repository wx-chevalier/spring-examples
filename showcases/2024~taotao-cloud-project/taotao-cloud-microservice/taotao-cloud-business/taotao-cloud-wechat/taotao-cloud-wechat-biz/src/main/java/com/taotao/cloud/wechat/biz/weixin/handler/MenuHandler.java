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

package com.taotao.cloud.wechat.biz.weixin.handler;

import static me.chanjar.weixin.common.api.WxConsts.EventType;

import org.dromara.hutooljson.JSONObject;
import org.dromara.hutooljson.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.weixin.config.CommonConstants;
import com.joolun.weixin.constant.ConfigConstant;
import com.joolun.weixin.entity.WxMenu;
import com.joolun.weixin.entity.WxMsg;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.mapper.WxMenuMapper;
import com.joolun.weixin.mapper.WxMsgMapper;
import com.joolun.weixin.mapper.WxUserMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.builder.outxml.*;
import org.springframework.stereotype.Component;

/**
 * @author www.joolun.com
 */
@Slf4j
@Component
@AllArgsConstructor
public class MenuHandler extends AbstractHandler {

    private final WxMenuMapper wxMenuMapper;
    private final WxUserMapper wxUserMapper;
    private final WxMsgMapper wxMsgMapper;

    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMessage,
            Map<String, Object> context,
            WxMpService weixinService,
            WxSessionManager sessionManager)
            throws WxErrorException {
        // 消息记录
        WxMenu wxMenu = null;
        if (EventType.CLICK.equals(wxMessage.getEvent()) || EventType.SCANCODE_WAITMSG.equals(wxMessage.getEvent())) {
            wxMenu = wxMenuMapper.selectById(wxMessage.getEventKey());
            if (wxMenu == null) { // 菜单过期
                return new TextBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .content("非常抱歉，该菜单已删除！")
                        .build();
            }
        } else {
            wxMenu = new WxMenu();
        }
        WxUser wxUser =
                wxUserMapper.selectOne(Wrappers.<WxUser>lambdaQuery().eq(WxUser::getOpenId, wxMessage.getFromUser()));
        if (wxUser == null) { // 库中无此用户
            WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
            wxUser = new WxUser();
            wxUser.setSubscribeNum(1);
            SubscribeHandler.setWxUserValue(wxUser, userWxInfo);
            wxUserMapper.insert(wxUser);
        }
        // 组装菜单回复消息
        return getWxMpXmlOutMessage(wxMessage, wxMenu, wxUser);
    }

    /**
     * 组装菜单回复消息
     *
     * @param wxMessage
     * @param wxMenu
     * @return
     */
    public WxMpXmlOutMessage getWxMpXmlOutMessage(WxMpXmlMessage wxMessage, WxMenu wxMenu, WxUser wxUser) {
        WxMpXmlOutMessage wxMpXmlOutMessage = null;
        // 记录接收消息
        WxMsg wxMsg = new WxMsg();
        //		wxMsg.setTenantId(wxApp.getTenantId());
        wxMsg.setWxUserId(wxUser.getId());
        wxMsg.setNickName(wxUser.getNickName());
        wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
        wxMsg.setType(ConfigConstant.WX_MSG_TYPE_1);
        wxMsg.setRepEvent(wxMessage.getEvent());
        wxMsg.setRepType(wxMessage.getMsgType());
        wxMsg.setRepName(wxMenu.getName());
        if (EventType.VIEW.equals(wxMessage.getEvent())) {
            wxMsg.setRepUrl(wxMessage.getEventKey());
        }
        if (EventType.SCANCODE_WAITMSG.equals(wxMessage.getEvent())) {
            wxMsg.setRepContent(wxMessage.getScanCodeInfo().getScanResult());
        }
        wxMsg.setReadFlag(CommonConstants.NO);
        LocalDateTime now = LocalDateTime.now();
        wxMsg.setCreateTime(now);
        wxMsgMapper.insert(wxMsg);
        if (WxConsts.MenuButtonType.CLICK.equals(wxMenu.getType())
                || WxConsts.MenuButtonType.SCANCODE_WAITMSG.equals(wxMenu.getType())) {
            // 记录回复消息
            wxMsg = new WxMsg();
            wxMsg.setWxUserId(wxUser.getId());
            wxMsg.setNickName(wxUser.getNickName());
            wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
            wxMsg.setCreateTime(now.plusSeconds(1));
            wxMsg.setType(ConfigConstant.WX_MSG_TYPE_2);
            wxMsg.setRepType(wxMenu.getRepType());
            if (WxConsts.KefuMsgType.TEXT.equals(wxMenu.getRepType())) {
                wxMsg.setRepContent(wxMenu.getRepContent());
                wxMpXmlOutMessage = new TextBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .content(wxMenu.getRepContent())
                        .build();
            }
            if (WxConsts.KefuMsgType.IMAGE.equals(wxMenu.getRepType())) {
                wxMsg.setRepName(wxMenu.getRepName());
                wxMsg.setRepUrl(wxMenu.getRepUrl());
                wxMsg.setRepMediaId(wxMenu.getRepMediaId());
                wxMpXmlOutMessage = new ImageBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(wxMenu.getRepMediaId())
                        .build();
            }
            if (WxConsts.KefuMsgType.VOICE.equals(wxMenu.getRepType())) {
                wxMsg.setRepName(wxMenu.getRepName());
                wxMsg.setRepUrl(wxMenu.getRepUrl());
                wxMsg.setRepMediaId(wxMenu.getRepMediaId());
                wxMpXmlOutMessage = new VoiceBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(wxMenu.getRepMediaId())
                        .build();
            }
            if (WxConsts.KefuMsgType.VIDEO.equals(wxMenu.getRepType())) {
                wxMsg.setRepName(wxMenu.getRepName());
                wxMsg.setRepDesc(wxMenu.getRepDesc());
                wxMsg.setRepUrl(wxMenu.getRepUrl());
                wxMsg.setRepMediaId(wxMenu.getRepMediaId());
                wxMpXmlOutMessage = new VideoBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(wxMenu.getRepMediaId())
                        .title(wxMenu.getRepName())
                        .description(wxMenu.getRepDesc())
                        .build();
            }
            if (WxConsts.KefuMsgType.MUSIC.equals(wxMenu.getRepType())) {
                wxMsg.setRepName(wxMenu.getRepName());
                wxMsg.setRepDesc(wxMenu.getRepDesc());
                wxMsg.setRepUrl(wxMenu.getRepUrl());
                wxMsg.setRepHqUrl(wxMenu.getRepHqUrl());
                wxMsg.setRepThumbMediaId(wxMenu.getRepThumbMediaId());
                wxMsg.setRepThumbUrl(wxMenu.getRepThumbUrl());
                wxMpXmlOutMessage = new MusicBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .thumbMediaId(wxMenu.getRepThumbMediaId())
                        .title(wxMenu.getRepName())
                        .description(wxMenu.getRepDesc())
                        .musicUrl(wxMenu.getRepUrl())
                        .hqMusicUrl(wxMenu.getRepHqUrl())
                        .build();
            }
            if (WxConsts.KefuMsgType.NEWS.equals(wxMenu.getRepType())) {
                List<WxMpXmlOutNewsMessage.Item> list = new ArrayList<>();
                List<JSONObject> listJSONObject =
                        JSONUtil.toList(wxMenu.getContent().getJSONArray("articles"), JSONObject.class);
                WxMpXmlOutNewsMessage.Item t;
                for (JSONObject jSONObject : listJSONObject) {
                    t = new WxMpXmlOutNewsMessage.Item();
                    t.setTitle(jSONObject.getStr("title"));
                    t.setDescription(jSONObject.getStr("digest"));
                    t.setPicUrl(jSONObject.getStr("thumbUrl"));
                    t.setUrl(jSONObject.getStr("url"));
                    list.add(t);
                }
                wxMsg.setRepName(wxMenu.getRepName());
                wxMsg.setRepDesc(wxMenu.getRepDesc());
                wxMsg.setRepUrl(wxMenu.getRepUrl());
                wxMsg.setRepMediaId(wxMenu.getRepMediaId());
                wxMsg.setContent(wxMenu.getContent());
                wxMpXmlOutMessage = new NewsBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .articles(list)
                        .build();
            }
            wxMsgMapper.insert(wxMsg);
        }
        return wxMpXmlOutMessage;
    }
}
