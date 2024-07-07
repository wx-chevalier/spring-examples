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

package com.taotao.cloud.wechat.biz.mp.controller.admin.material.vo;

import org.dromara.hutoolcore.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.web.multipart.MultipartFile;

@ApiModel("管理后台 - 公众号素材上传永久 Request VO")
@Data
public class MpMaterialUploadPermanentReqVO {

    @ApiModelProperty(value = "公众号账号的编号", required = true, example = "2048")
    @NotNull(message = "公众号账号的编号不能为空")
    private Long accountId;

    @ApiModelProperty(value = "文件类型", required = true, example = "image", notes = "参见 WxConsts.MediaFileType 枚举")
    @NotEmpty(message = "文件类型不能为空")
    private String type;

    @ApiModelProperty(value = "文件附件", required = true)
    @NotNull(message = "文件不能为空")
    @JsonIgnore // 避免被操作日志，进行序列化，导致报错
    private MultipartFile file;

    @ApiModelProperty(value = "名字", example = "wechat.mp", notes = "如果 name 为空，则使用 file 文件名")
    private String name;

    @ApiModelProperty(value = "视频素材的标题", example = "视频素材的标题", notes = "文件类型为 video 时，必填")
    private String title;

    @ApiModelProperty(value = "视频素材的描述", example = "视频素材的描述", notes = "文件类型为 video 时，必填")
    private String introduction;

    @AssertTrue(message = "标题不能为空")
    public boolean isTitleValid() {
        // 生成场景为管理后台时，必须设置上级菜单，不然生成的菜单 SQL 是无父级菜单的
        return ObjectUtil.notEqual(type, WxConsts.MediaFileType.VIDEO) || title != null;
    }

    @AssertTrue(message = "描述不能为空")
    public boolean isIntroductionValid() {
        // 生成场景为管理后台时，必须设置上级菜单，不然生成的菜单 SQL 是无父级菜单的
        return ObjectUtil.notEqual(type, WxConsts.MediaFileType.VIDEO) || introduction != null;
    }
}
