package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.conf.oss.OssTemplate;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysEmail;
import com.fc.v2.service.ITSysEmailService;
import com.fc.v2.util.SimpleEmailUtil;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 邮件发送Controller
 *
 * @author fuce
 * @version V1.0
 * @ClassName: EmailController
 * @date 2019-06-10 00:39
 */
@Api(value = "邮件发送Controller")
@Controller
@RequestMapping("/EmailController")
public class SysEmailController extends BaseController {

    /**
     * 跳转页面参数
     */
    private final String prefix = "admin/sysEmail";

    @Autowired
    private ITSysEmailService tSysEmailService;

    @Autowired
    private OssTemplate template;

    /**
     * 分页展示页面
     *
     * @param model
     * @return String
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("system:email:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 分页list页面
     *
     * @param tSysEmail
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("system:email:list")
    @ResponseBody
    public ResultTable list(TSysEmail tSysEmail) {
        QueryWrapper<TSysEmail> queryWrapper = new QueryWrapper<TSysEmail>();
        queryWrapper.like(StringUtils.isNotEmpty(tSysEmail.getTitle()), "title", tSysEmail.getTitle());
        queryWrapper.eq(StringUtils.isNotEmpty(tSysEmail.getCreateBy()),"create_by",tSysEmail.getCreateBy());

        startPage();
        PageInfo<TSysEmail> page =  new PageInfo<TSysEmail>(tSysEmailService.selectTSysEmailList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 新增跳转
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.put("BucketName", template.getOssProperties().getBucketName());
        return prefix + "/add";
    }

    /**
     * 新增
     *
     * @param tSysEmail
     * @param model
     * @return
     * @throws Exception
     * @author fuce
     */
    @Log(title = "新增邮件", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("system:email:add")
    @ResponseBody
    public AjaxResult add(@RequestBody TSysEmail tSysEmail, Model model) throws Exception {
        if (tSysEmailService.insertTSysEmail(tSysEmail) > 0){
            //发送邮件
            SimpleEmailUtil.sendEmail(tSysEmail);
            return success();
        }

        return error();
    }

    /**
     * 删除邮件
     *
     * @param ids
     * @return
     */
    @Log(title = "删除邮件", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("system:email:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tSysEmailService.deleteTSysEmailByIds(ids));
    }

    /**
     * 检查邮件同名
     *
     * @param tSysEmail
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysEmail tSysEmail) {
        return tSysEmailService.checkNameUnique(tSysEmail) > 0 ? 1 : 0;
    }


    /**
     * 修改跳转
     *
     * @param id
     * @param mmap
     * @return
     */
    @ApiOperation(value = "修改跳转", notes = "修改跳转")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("BucketName", template.getOssProperties().getBucketName());
        mmap.put("TSysEmail", tSysEmailService.selectTSysEmailById(id));
        return prefix + "/view";
    }
}
