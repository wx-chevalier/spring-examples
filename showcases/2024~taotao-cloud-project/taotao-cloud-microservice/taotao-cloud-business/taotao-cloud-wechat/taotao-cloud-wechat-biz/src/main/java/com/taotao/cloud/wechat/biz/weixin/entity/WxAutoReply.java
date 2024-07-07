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

package com.taotao.cloud.wechat.biz.weixin.entity;

import org.dromara.hutooljson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.joolun.framework.config.typehandler.JsonTypeHandler;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

/**
 * 消息自动回复
 *
 * @author www.joolun.com
 * @since 2019-04-18 15:40:39
 */
@Data
@TableName("wx_auto_reply")
@EqualsAndHashCode(callSuper = true)
public class WxAutoReply extends Model<WxAutoReply> {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /** 创建者 */
    private String createId;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新者 */
    private String updateId;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 备注 */
    private String remark;
    /** 逻辑删除标记（0：显示；1：隐藏） */
    private String delFlag;
    /** 类型（1、关注时回复；2、消息回复；3、关键词回复） */
    @NotNull(message = "类型不能为空")
    private String type;
    /** 关键词 */
    private String reqKey;
    /** 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置） */
    private String reqType;
    /** 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文） */
    @NotNull(message = "回复消息类型不能为空")
    private String repType;
    /** 回复类型文本匹配类型（1、全匹配，2、半匹配） */
    private String repMate;
    /** 回复类型文本保存文字 */
    private String repContent;
    /** 回复的素材名、视频和音乐的标题 */
    private String repName;
    /** 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id */
    private String repMediaId;
    /** 视频和音乐的描述 */
    private String repDesc;
    /** 链接 */
    private String repUrl;
    /** 高质量链接 */
    private String repHqUrl;
    /** 缩略图的媒体id */
    private String repThumbMediaId;
    /** 缩略图url */
    private String repThumbUrl;

    /** 图文消息的内容 */
    @TableField(typeHandler = JsonTypeHandler.class, jdbcType = JdbcType.VARCHAR)
    private JSONObject content;
}
