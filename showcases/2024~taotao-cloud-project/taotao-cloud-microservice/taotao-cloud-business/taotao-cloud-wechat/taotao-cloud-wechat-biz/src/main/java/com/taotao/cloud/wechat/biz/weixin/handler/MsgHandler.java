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

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

import org.dromara.hutooljson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joolun.weixin.config.CommonConstants;
import com.joolun.weixin.constant.ConfigConstant;
import com.joolun.weixin.constant.WebSocketConstant;
import com.joolun.weixin.entity.WxAutoReply;
import com.joolun.weixin.entity.WxMsg;
import com.joolun.weixin.entity.WxUser;
import com.joolun.weixin.mapper.WxUserMapper;
import com.joolun.weixin.service.WxAutoReplyService;
import com.joolun.weixin.service.WxMsgService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.builder.outxml.*;
import org.springframework.stereotype.Component;

/**
 * @author www.joolun.com
 */
@Component
@AllArgsConstructor
public class MsgHandler extends AbstractHandler {

    private final WxAutoReplyService wxAutoReplyService;
    private final WxUserMapper wxUserMapper;
    private final WxMsgService wxMsgService;

    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMessage,
            Map<String, Object> context,
            WxMpService wxMpService,
            WxSessionManager sessionManager) {
        // 组装回复消息
        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            WxMpXmlOutMessage rs;
            // TODO 可以选择将消息保存到本地
            WxUser wxUser = wxUserMapper.selectOne(
                    Wrappers.<WxUser>lambdaQuery().eq(WxUser::getOpenId, wxMessage.getFromUser()));
            if (WxConsts.KefuMsgType.TEXT.equals(wxMessage.getMsgType())) { // 1、先处理是否有文本关键字回复
                // 先全匹配
                List<WxAutoReply> listWxAutoReply = wxAutoReplyService.list(Wrappers.<WxAutoReply>query()
                        .lambda()
                        .eq(WxAutoReply::getType, ConfigConstant.WX_AUTO_REPLY_TYPE_3)
                        .eq(WxAutoReply::getRepMate, ConfigConstant.WX_REP_MATE_1)
                        .eq(WxAutoReply::getReqKey, wxMessage.getContent()));
                if (listWxAutoReply != null && listWxAutoReply.size() > 0) {
                    rs = this.getWxMpXmlOutMessage(wxMessage, listWxAutoReply, wxUser, wxMsgService);
                    if (rs != null) {
                        return rs;
                    }
                }
                // 再半匹配
                listWxAutoReply = wxAutoReplyService.list(Wrappers.<WxAutoReply>query()
                        .lambda()
                        .eq(WxAutoReply::getType, ConfigConstant.WX_AUTO_REPLY_TYPE_3)
                        .eq(WxAutoReply::getRepMate, ConfigConstant.WX_REP_MATE_2)
                        .like(WxAutoReply::getReqKey, wxMessage.getContent()));
                if (listWxAutoReply != null && listWxAutoReply.size() > 0) {
                    rs = this.getWxMpXmlOutMessage(wxMessage, listWxAutoReply, wxUser, wxMsgService);
                    if (rs != null) {
                        return rs;
                    }
                }
            }
            // 2、再处理消息回复
            List<WxAutoReply> listWxAutoReply = wxAutoReplyService.list(Wrappers.<WxAutoReply>query()
                    .lambda()
                    .eq(WxAutoReply::getType, ConfigConstant.WX_AUTO_REPLY_TYPE_2)
                    .eq(WxAutoReply::getReqType, wxMessage.getMsgType()));
            rs = this.getWxMpXmlOutMessage(wxMessage, listWxAutoReply, wxUser, wxMsgService);
            return rs;
        }
        return null;
    }

    /**
     * 组装回复消息，并记录消息
     *
     * @param wxMessage
     * @param listWxAutoReply
     * @return
     */
    public static WxMpXmlOutMessage getWxMpXmlOutMessage(
            WxMpXmlMessage wxMessage, List<WxAutoReply> listWxAutoReply, WxUser wxUser, WxMsgService wxMsgService) {
        WxMpXmlOutMessage wxMpXmlOutMessage = null;
        // 记录接收消息
        WxMsg wxMsg = new WxMsg();
        wxMsg.setWxUserId(wxUser.getId());
        wxMsg.setNickName(wxUser.getNickName());
        wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
        wxMsg.setType(ConfigConstant.WX_MSG_TYPE_1);
        wxMsg.setRepEvent(wxMessage.getEvent());
        wxMsg.setRepType(wxMessage.getMsgType());
        wxMsg.setRepMediaId(wxMessage.getMediaId());
        if (XmlMsgType.TEXT.equals(wxMessage.getMsgType())) {
            wxMsg.setRepContent(wxMessage.getContent());
        }
        if (XmlMsgType.VOICE.equals(wxMessage.getMsgType())) {
            wxMsg.setRepName(wxMessage.getMediaId() + "." + wxMessage.getFormat());
            wxMsg.setRepContent(wxMessage.getRecognition());
        }
        if (XmlMsgType.IMAGE.equals(wxMessage.getMsgType())) {
            wxMsg.setRepUrl(wxMessage.getPicUrl());
        }
        if (XmlMsgType.LINK.equals(wxMessage.getMsgType())) {
            wxMsg.setRepName(wxMessage.getTitle());
            wxMsg.setRepDesc(wxMessage.getDescription());
            wxMsg.setRepUrl(wxMessage.getUrl());
        }
        if (WxConsts.MediaFileType.FILE.equals(wxMessage.getMsgType())) {
            wxMsg.setRepName(wxMessage.getTitle());
            wxMsg.setRepDesc(wxMessage.getDescription());
        }
        if (XmlMsgType.VIDEO.equals(wxMessage.getMsgType())) {
            wxMsg.setRepThumbMediaId(wxMessage.getThumbMediaId());
        }
        if (XmlMsgType.LOCATION.equals(wxMessage.getMsgType())) {
            wxMsg.setRepLocationX(wxMessage.getLocationX());
            wxMsg.setRepLocationY(wxMessage.getLocationY());
            wxMsg.setRepScale(wxMessage.getScale());
            wxMsg.setRepContent(wxMessage.getLabel());
        }
        wxMsg.setReadFlag(CommonConstants.NO);
        LocalDateTime now = LocalDateTime.now();
        wxMsg.setCreateTime(now);
        wxMsgService.save(wxMsg);
        // 推送websocket
        String destination = WebSocketConstant.USER_DESTINATION_PREFIX + WebSocketConstant.WX_MSG + wxMsg.getWxUserId();
        if (listWxAutoReply != null && listWxAutoReply.size() > 0) {
            WxAutoReply wxAutoReply = listWxAutoReply.get(0);
            // 记录回复消息
            wxMsg = new WxMsg();
            wxMsg.setWxUserId(wxUser.getId());
            wxMsg.setNickName(wxUser.getNickName());
            wxMsg.setHeadimgUrl(wxUser.getHeadimgUrl());
            wxMsg.setCreateTime(now.plusSeconds(1));
            wxMsg.setType(ConfigConstant.WX_MSG_TYPE_2);
            wxMsg.setRepType(wxAutoReply.getRepType());

            if (WxConsts.KefuMsgType.TEXT.equals(wxAutoReply.getRepType())) { // 文本
                wxMsg.setRepContent(wxAutoReply.getRepContent());
                wxMpXmlOutMessage = new TextBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .content(wxAutoReply.getRepContent())
                        .build();
            }
            if (WxConsts.KefuMsgType.IMAGE.equals(wxAutoReply.getRepType())) { // 图片
                wxMsg.setRepName(wxAutoReply.getRepName());
                wxMsg.setRepUrl(wxAutoReply.getRepUrl());
                wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
                wxMpXmlOutMessage = new ImageBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(wxAutoReply.getRepMediaId())
                        .build();
            }
            if (WxConsts.KefuMsgType.VOICE.equals(wxAutoReply.getRepType())) {
                wxMsg.setRepName(wxAutoReply.getRepName());
                wxMsg.setRepUrl(wxAutoReply.getRepUrl());
                wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
                wxMpXmlOutMessage = new VoiceBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(wxAutoReply.getRepMediaId())
                        .build();
            }
            if (WxConsts.KefuMsgType.VIDEO.equals(wxAutoReply.getRepType())) {
                wxMsg.setRepName(wxAutoReply.getRepName());
                wxMsg.setRepDesc(wxAutoReply.getRepDesc());
                wxMsg.setRepUrl(wxAutoReply.getRepUrl());
                wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
                wxMpXmlOutMessage = new VideoBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(wxAutoReply.getRepMediaId())
                        .title(wxAutoReply.getRepName())
                        .description(wxAutoReply.getRepDesc())
                        .build();
            }
            if (WxConsts.KefuMsgType.MUSIC.equals(wxAutoReply.getRepType())) {
                wxMsg.setRepName(wxAutoReply.getRepName());
                wxMsg.setRepDesc(wxAutoReply.getRepDesc());
                wxMsg.setRepUrl(wxAutoReply.getRepUrl());
                wxMsg.setRepHqUrl(wxAutoReply.getRepHqUrl());
                wxMsg.setRepThumbMediaId(wxAutoReply.getRepThumbMediaId());
                wxMsg.setRepThumbUrl(wxAutoReply.getRepThumbUrl());
                wxMpXmlOutMessage = new MusicBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .thumbMediaId(wxAutoReply.getRepThumbMediaId())
                        .title(wxAutoReply.getRepName())
                        .description(wxAutoReply.getRepDesc())
                        .musicUrl(wxAutoReply.getRepUrl())
                        .hqMusicUrl(wxAutoReply.getRepHqUrl())
                        .build();
            }
            if (WxConsts.KefuMsgType.NEWS.equals(wxAutoReply.getRepType())) {
                List<WxMpXmlOutNewsMessage.Item> list = new ArrayList<>();
                List<JSONObject> listJSONObject =
                        wxAutoReply.getContent().getJSONArray("articles").toList(JSONObject.class);
                WxMpXmlOutNewsMessage.Item t;
                for (JSONObject jSONObject : listJSONObject) {
                    t = new WxMpXmlOutNewsMessage.Item();
                    t.setTitle(jSONObject.getStr("title"));
                    t.setDescription(jSONObject.getStr("digest"));
                    t.setPicUrl(jSONObject.getStr("thumbUrl"));
                    t.setUrl(jSONObject.getStr("url"));
                    list.add(t);
                }
                wxMsg.setRepName(wxAutoReply.getRepName());
                wxMsg.setRepDesc(wxAutoReply.getRepDesc());
                wxMsg.setRepUrl(wxAutoReply.getRepUrl());
                wxMsg.setRepMediaId(wxAutoReply.getRepMediaId());
                wxMsg.setContent(wxAutoReply.getContent());
                wxMpXmlOutMessage = new NewsBuilder()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .articles(list)
                        .build();
            }
            wxMsgService.save(wxMsg);
        }
        return wxMpXmlOutMessage;
    }
}
