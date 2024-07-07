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

package com.taotao.cloud.im.biz.platform.modules.push.vo;

import com.platform.modules.push.enums.PushBodyTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** 推送消息体 */
@Data
@Accessors(chain = true) // 链式调用
@NoArgsConstructor
public class PushBodyVo {

    /** 消息id */
    private String msgId;

    /** 消息类型（MSG/BIG/NOTICE） */
    private String pushType;

    /** 消息时间 */
    private String createTime;

    /** 消息数据 */
    private Object msgContent;

    /** 发送人数据 */
    private PushFromVo fromInfo;

    /** 接收人数据 */
    private PushToVo groupInfo;

    public PushBodyVo(String msgId, PushBodyTypeEnum pushType, Object data) {
        this.msgId = msgId;
        this.pushType = pushType.getCode();
        this.msgContent = data;
        this.createTime = DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_FORMAT);
        this.groupInfo = new PushToVo();
    }
}
