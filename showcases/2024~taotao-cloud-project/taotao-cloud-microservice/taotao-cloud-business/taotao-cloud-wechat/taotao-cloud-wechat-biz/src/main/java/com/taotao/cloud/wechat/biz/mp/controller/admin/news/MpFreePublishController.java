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

package com.taotao.cloud.wechat.biz.mp.controller.admin.news;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.collection.MapUtils.findAndThen;
import static cn.iocoder.yudao.module.mp.enums.ErrorCodeConstants.*;

import org.dromara.hutoolcore.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.object.PageUtils;
import cn.iocoder.yudao.module.mp.controller.admin.news.vo.MpFreePublishPageReqVO;
import cn.iocoder.yudao.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.iocoder.yudao.module.mp.framework.mp.core.MpServiceFactory;
import cn.iocoder.yudao.module.mp.service.material.MpMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishItem;
import me.chanjar.weixin.mp.bean.freepublish.WxMpFreePublishList;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "管理后台 - 公众号发布能力")
@RestController
@RequestMapping("/mp/free-publish")
@Validated
public class MpFreePublishController {

    @Resource
    private MpServiceFactory mpServiceFactory;

    @Resource
    private MpMaterialService mpMaterialService;

    @GetMapping("/page")
    @ApiOperation("获得已发布的图文分页")
    @PreAuthorize("@ss.hasPermission('mp:free-publish:query')")
    public CommonResult<PageResult<WxMpFreePublishItem>> getFreePublishPage(MpFreePublishPageReqVO reqVO) {
        // 从公众号查询已发布的图文列表
        WxMpService mpService = mpServiceFactory.getRequiredMpService(reqVO.getAccountId());
        WxMpFreePublishList publicationRecords;
        try {
            publicationRecords = mpService
                    .getFreePublishService()
                    .getPublicationRecords(PageUtils.getStart(reqVO), reqVO.getPageSize());
        } catch (WxErrorException e) {
            throw exception(FREE_PUBLISH_LIST_FAIL, e.getError().getErrorMsg());
        }
        // 查询对应的图片地址。目的：解决公众号的图片链接无法在我们后台展示
        setFreePublishThumbUrl(publicationRecords.getItems());

        // 返回分页
        return success(new PageResult<>(
                publicationRecords.getItems(),
                publicationRecords.getTotalCount().longValue()));
    }

    private void setFreePublishThumbUrl(List<WxMpFreePublishItem> items) {
        // 1.1 获得 mediaId 数组
        Set<String> mediaIds = new HashSet<>();
        items.forEach(
                item -> item.getContent().getNewsItem().forEach(newsItem -> mediaIds.add(newsItem.getThumbMediaId())));
        if (CollUtil.isEmpty(mediaIds)) {
            return;
        }
        // 1.2 批量查询对应的 Media 素材
        Map<String, MpMaterialDO> materials = CollectionUtils.convertMap(
                mpMaterialService.getMaterialListByMediaId(mediaIds), MpMaterialDO::getMediaId);

        // 2. 设置回 WxMpFreePublishItem 记录
        items.forEach(item -> item.getContent()
                .getNewsItem()
                .forEach(newsItem -> findAndThen(
                        materials, newsItem.getThumbMediaId(), material -> newsItem.setThumbUrl(material.getUrl()))));
    }

    @PostMapping("/submit")
    @ApiOperation("发布草稿")
    @ApiImplicitParams({
        @ApiImplicitParam(
                name = "accountId",
                value = "公众号账号的编号",
                required = true,
                example = "1024",
                dataTypeClass = Long.class),
        @ApiImplicitParam(
                name = "mediaId",
                value = "要发布的草稿的 media_id",
                required = true,
                example = "2048",
                dataTypeClass = String.class)
    })
    @PreAuthorize("@ss.hasPermission('mp:free-publish:submit')")
    public CommonResult<String> submitFreePublish(
            @RequestParam("accountId") Long accountId, @RequestParam("mediaId") String mediaId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            String publishId = mpService.getFreePublishService().submit(mediaId);
            return success(publishId);
        } catch (WxErrorException e) {
            throw exception(FREE_PUBLISH_SUBMIT_FAIL, e.getError().getErrorMsg());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除草稿")
    @ApiImplicitParams({
        @ApiImplicitParam(
                name = "accountId",
                value = "公众号账号的编号",
                required = true,
                example = "1024",
                dataTypeClass = Long.class),
        @ApiImplicitParam(
                name = "articleId",
                value = "发布记录的编号",
                required = true,
                example = "2048",
                dataTypeClass = String.class)
    })
    @PreAuthorize("@ss.hasPermission('mp:free-publish:delete')")
    public CommonResult<Boolean> deleteFreePublish(
            @RequestParam("accountId") Long accountId, @RequestParam("articleId") String articleId) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        try {
            mpService.getFreePublishService().deletePushAllArticle(articleId);
            return success(true);
        } catch (WxErrorException e) {
            throw exception(FREE_PUBLISH_DELETE_FAIL, e.getError().getErrorMsg());
        }
    }
}
