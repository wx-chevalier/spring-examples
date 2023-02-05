package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.conf.oss.OssTemplate;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;

/**
 * 文件上传controller
 *
 * @author fuce
 * @date: 2018年9月16日 下午4:23:50
 */
@Api(value = "文件上传")
@Controller
@RequestMapping("/FileController")
public class SysFileController extends BaseController {

    /**
     * 跳转页面参数
     */
    private final String prefix = "admin/sysFile";

    @Autowired
    private OssTemplate template;

    /**
     * 分页展示页面
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月20日 下午10:18:32
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("system:file:view")
    public String view(ModelMap model) {
        model.put("bucketURL", template.getOssProperties().getEndpoint() + "/" + template.getOssProperties().getBucketName());
        return prefix + "/list";
    }

    /**
     * 文件列表
     *
     * @param tSysFile
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("system:file:list")
    @ResponseBody
    public ResultTable list(TSysFile tSysFile) {
        QueryWrapper<TSysFile> queryWrapper = new QueryWrapper<TSysFile>();
        queryWrapper.eq(StringUtils.isNotEmpty(tSysFile.getCreateBy()), "create_by", tSysFile.getCreateBy());
        queryWrapper.like(StringUtils.isNotEmpty(tSysFile.getFileName()), "file_name", tSysFile.getFileName());
        
        startPage();
        PageInfo<TSysFile> page = new PageInfo<TSysFile>(sysFileService.selectTSysFileList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 新增文件跳转页面
     *
     * @return
     * @author fuce
     * @Date 2019年11月20日 下午10:19:03
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap map) {
        map.put("BucketName", template.getOssProperties().getBucketName());
        return prefix + "/add";
    }


    /**
     * 检查文件名字
     *
     * @param tsysFile
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysFile tsysFile) {
        return sysFileService.checkNameUnique(tsysFile) > 0 ? 1 : 0;
    }


    /**
     * 修改文件
     *
     * @param id
     * @param map
     * @return
     */
    @ApiOperation(value = "修改跳转", notes = "修改跳转")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap map) {
        map.put("BucketName", template.getOssProperties().getBucketName());
        map.put("sysFile", sysFileService.selectTSysFileById(id));
        return prefix + "/edit";
    }


    /**
     * 删除文件
     *
     * @param ids
     * @return
     */
    @Log(title = "删除日志", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("system:file:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysFileService.deleteTSysFileByIds(ids));
    }
}
