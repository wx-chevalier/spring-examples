package com.suke.czx.modules.apk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.apk.entity.ApkVersion;
import com.suke.czx.modules.apk.service.ApkVersionService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * APK版本管理
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2019-04-28 15:56:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/apk/version")
@Api(value = "ApkVersionController", tags = "APK版本管理")
public class ApkVersionController extends AbstractController {
    private final ApkVersionService apkVersionService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('apkversion:apkversion:list')")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        QueryWrapper<ApkVersion> queryWrapper = new QueryWrapper<>();
        IPage<ApkVersion> sysConfigList = apkVersionService.page(mpPageConvert.<ApkVersion>pageParamConvert(params), queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }


    /**
     * 信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('apkversion:apkversion:info')")
    public R info(@PathVariable("id") Long id) {
        return R.ok().put("apkVersion", apkVersionService.getById(id));
    }


    /**
     * 新增APK版本管理
     */
    @SysLog("新增APK版本管理数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasRole('apkversion:apkversion:save')")
    public R save(@RequestBody ApkVersion apkVersion) {
        apkVersion.setUserId(getUserId());
        apkVersionService.save(apkVersion);
        return R.ok();
    }


    /**
     * 修改
     */
    @SysLog("修改APK版本管理数据")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('apkversion:apkversion:update')")
    public R update(@RequestBody ApkVersion apkVersion) {
        apkVersion.setUpdateTime(new Date());
        apkVersionService.updateById(apkVersion);
        return R.ok();
    }


    /**
     * 删除
     */
    @SysLog("删除APK版本管理数据")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @PreAuthorize("hasRole('apkversion:apkversion:delete')")
    public R delete(@RequestBody Long[] ids) {
        apkVersionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
