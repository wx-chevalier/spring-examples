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

package com.taotao.cloud.wechat.biz.mp.controller.admin.tag;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.mp.controller.admin.tag.vo.*;
import cn.iocoder.yudao.module.mp.convert.tag.MpTagConvert;
import cn.iocoder.yudao.module.mp.dal.dataobject.tag.MpTagDO;
import cn.iocoder.yudao.module.mp.service.tag.MpTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "管理后台 - 公众号标签")
@RestController
@RequestMapping("/mp/tag")
@Validated
public class MpTagController {

    @Resource
    private MpTagService mpTagService;

    @PostMapping("/create")
    @ApiOperation("创建公众号标签")
    @PreAuthorize("@ss.hasPermission('mp:tag:create')")
    public CommonResult<Long> createTag(@Valid @RequestBody MpTagCreateReqVO createReqVO) {
        return success(mpTagService.createTag(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新公众号标签")
    @PreAuthorize("@ss.hasPermission('mp:tag:update')")
    public CommonResult<Boolean> updateTag(@Valid @RequestBody MpTagUpdateReqVO updateReqVO) {
        mpTagService.updateTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除公众号标签")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mp:tag:delete')")
    public CommonResult<Boolean> deleteTag(@RequestParam("id") Long id) {
        mpTagService.deleteTag(id);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获取公众号标签分页")
    @PreAuthorize("@ss.hasPermission('mp:tag:query')")
    public CommonResult<PageResult<MpTagRespVO>> getTagPage(MpTagPageReqVO pageReqVO) {
        PageResult<MpTagDO> pageResult = mpTagService.getTagPage(pageReqVO);
        return success(MpTagConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取公众号账号精简信息列表")
    @PreAuthorize("@ss.hasPermission('mp:account:query')")
    public CommonResult<List<MpTagSimpleRespVO>> getSimpleTags() {
        List<MpTagDO> list = mpTagService.getTagList();
        return success(MpTagConvert.INSTANCE.convertList02(list));
    }

    @PostMapping("/sync")
    @ApiOperation("同步公众号标签")
    @ApiImplicitParam(name = "accountId", value = "公众号账号的编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mp:tag:sync')")
    public CommonResult<Boolean> syncTag(@RequestParam("accountId") Long accountId) {
        mpTagService.syncTag(accountId);
        return success(true);
    }
}
