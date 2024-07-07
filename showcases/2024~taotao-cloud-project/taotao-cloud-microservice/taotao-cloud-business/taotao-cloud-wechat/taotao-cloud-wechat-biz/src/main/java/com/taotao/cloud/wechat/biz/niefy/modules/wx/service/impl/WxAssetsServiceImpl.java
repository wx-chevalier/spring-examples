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

import com.github.niefy.modules.wx.dto.PageSizeConstant;
import com.github.niefy.modules.wx.service.WxAssetsService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service
@CacheConfig(cacheNames = {"wxAssetsServiceCache"})
@Slf4j
public class WxAssetsServiceImpl implements WxAssetsService {
    @Autowired
    WxMpService wxMpService;

    @Override
    @Cacheable(key = "methodName+ #appid")
    public WxMpMaterialCountResult materialCount(String appid) throws WxErrorException {
        log.info("从API获取素材总量");
        wxMpService.switchoverTo(appid);
        return wxMpService.getMaterialService().materialCount();
    }

    @Override
    @Cacheable(key = "methodName + #appid + #mediaId")
    public WxMpMaterialNews materialNewsInfo(String appid, String mediaId) throws WxErrorException {
        log.info("从API获取图文素材详情,mediaId={}", mediaId);
        wxMpService.switchoverTo(appid);
        return wxMpService.getMaterialService().materialNewsInfo(mediaId);
    }

    @Override
    @Cacheable(key = "methodName + #appid + #type + #page")
    public WxMpMaterialFileBatchGetResult materialFileBatchGet(String appid, String type, int page)
            throws WxErrorException {
        log.info("从API获取媒体素材列表,type={},page={}", type, page);
        wxMpService.switchoverTo(appid);
        final int pageSize = PageSizeConstant.PAGE_SIZE_SMALL;
        int offset = (page - 1) * pageSize;
        return wxMpService.getMaterialService().materialFileBatchGet(type, offset, pageSize);
    }

    @Cacheable(key = "methodName + #appid + #page")
    @Override
    public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(String appid, int page) throws WxErrorException {
        log.info("从API获取媒体素材列表,page={}", page);
        wxMpService.switchoverTo(appid);
        final int pageSize = PageSizeConstant.PAGE_SIZE_SMALL;
        int offset = (page - 1) * pageSize;
        return wxMpService.getMaterialService().materialNewsBatchGet(offset, pageSize);
    }

    @Override
    @CacheEvict(allEntries = true)
    public WxMpMaterialUploadResult materialNewsUpload(String appid, List<WxMpNewsArticle> articles)
            throws WxErrorException {
        Assert.notEmpty(articles, "图文列表不得为空");
        log.info("上传图文素材...");
        wxMpService.switchoverTo(appid);
        WxMpMaterialNews news = new WxMpMaterialNews();
        news.setArticles(articles);
        return wxMpService.getMaterialService().materialNewsUpload(news);
    }

    /**
     * 更新图文素材中的某篇文章
     *
     * @param appid
     * @param form
     */
    @Override
    @CacheEvict(allEntries = true)
    public void materialArticleUpdate(String appid, WxMpMaterialArticleUpdate form) throws WxErrorException {
        log.info("更新图文素材...");
        wxMpService.switchoverTo(appid);
        wxMpService.getMaterialService().materialNewsUpdate(form);
    }

    @Override
    @CacheEvict(allEntries = true)
    public WxMpMaterialUploadResult materialFileUpload(
            String appid, String mediaType, String fileName, MultipartFile file) throws WxErrorException, IOException {
        log.info("上传媒体素材：{}", fileName);
        wxMpService.switchoverTo(appid);
        String originalFilename = file.getOriginalFilename();
        File tempFile = File.createTempFile(
                fileName + "--", Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf(".")));
        file.transferTo(tempFile);
        WxMpMaterial wxMaterial = new WxMpMaterial();
        wxMaterial.setFile(tempFile);
        wxMaterial.setName(fileName);
        if (WxConsts.MediaFileType.VIDEO.equals(mediaType)) {
            wxMaterial.setVideoTitle(fileName);
        }
        WxMpMaterialUploadResult res = wxMpService.getMaterialService().materialFileUpload(mediaType, wxMaterial);
        tempFile.deleteOnExit();
        return res;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean materialDelete(String appid, String mediaId) throws WxErrorException {
        log.info("删除素材，mediaId={}", mediaId);
        wxMpService.switchoverTo(appid);
        return wxMpService.getMaterialService().materialDelete(mediaId);
    }
}
