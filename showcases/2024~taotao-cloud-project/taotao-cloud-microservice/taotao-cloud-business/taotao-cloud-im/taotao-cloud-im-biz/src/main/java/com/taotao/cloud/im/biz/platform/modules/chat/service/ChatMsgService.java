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

package com.taotao.cloud.im.biz.platform.modules.chat.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.chat.domain.ChatMsg;
import com.platform.modules.chat.vo.ChatVo01;
import com.platform.modules.chat.vo.ChatVo02;
import com.platform.modules.chat.vo.ChatVo03;
import com.platform.modules.push.vo.PushBodyVo;

/** 聊天消息 服务层 q3z3 */
public interface ChatMsgService extends BaseService<ChatMsg> {

    /** 发送消息 */
    ChatVo03 sendFriendMsg(ChatVo01 chatVo);

    /** 发送群消息 */
    ChatVo03 sendGroupMsg(ChatVo02 chatVo);

    /** 获取大消息 */
    PushBodyVo getBigMsg(String msgId);
}