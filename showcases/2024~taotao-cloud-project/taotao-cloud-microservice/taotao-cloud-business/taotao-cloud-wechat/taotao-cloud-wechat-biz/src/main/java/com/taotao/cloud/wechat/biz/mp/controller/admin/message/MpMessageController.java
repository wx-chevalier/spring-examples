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

package com.taotao.cloud.wechat.biz.mp.controller.admin.message;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import cn.iocoder.yudao.module.mp.controller.admin.message.vo.message.MpMessageRespVO;
import cn.iocoder.yudao.module.mp.controller.admin.message.vo.message.MpMessageSendReqVO;
import cn.iocoder.yudao.module.mp.convert.message.MpMessageConvert;
import cn.iocoder.yudao.module.mp.dal.dataobject.message.MpMessageDO;
import cn.iocoder.yudao.module.mp.service.message.MpMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "管理后台 - 公众号消息")
@RestController
@RequestMapping("/mp/message")
@Validated
public class MpMessageController {

    @Resource
    private MpMessageService mpMessageService;

    @GetMapping("/page")
    @ApiOperation("获得公众号消息分页")
    @PreAuthorize("@ss.hasPermission('mp:message:query')")
    public CommonResult<PageResult<MpMessageRespVO>> getMessagePage(@Valid MpMessagePageReqVO pageVO) {
        PageResult<MpMessageDO> pageResult = mpMessageService.getMessagePage(pageVO);
        return success(MpMessageConvert.INSTANCE.convertPage(pageResult));
    }

    @PostMapping("/send")
    @ApiOperation("给粉丝发送消息")
    @PreAuthorize("@ss.hasPermission('mp:message:send')")
    public CommonResult<MpMessageRespVO> sendMessage(@Valid @RequestBody MpMessageSendReqVO reqVO) {
        MpMessageDO message = mpMessageService.sendKefuMessage(reqVO);
        return success(MpMessageConvert.INSTANCE.convert(message));
    }
}
