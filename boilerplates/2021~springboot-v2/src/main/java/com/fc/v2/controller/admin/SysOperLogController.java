package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysOperLog;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 日志记录controller
 *
 * @author fuce
 * @date: 2018年9月30日 下午9:28:31
 */
@Controller
@Api(value = "日志记录")
@RequestMapping("/LogController")
public class SysOperLogController extends BaseController {

    /**
     * 跳转页面参数
     */
    private final String prefix = "admin/sysOperLog";

    /**
     * 日志记录展示页面
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("system:log:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 日志列表
     *
     * @param tSysOperLog
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("system:log:list")
    @ResponseBody
    public ResultTable list(TSysOperLog tSysOperLog) {
        QueryWrapper<TSysOperLog> queryWrapper = new QueryWrapper<TSysOperLog>();
        queryWrapper.like(StringUtils.isNotEmpty(tSysOperLog.getTitle()), "title", tSysOperLog.getTitle());
        queryWrapper.eq(StringUtils.isNotEmpty(tSysOperLog.getOperName()), "oper_name", tSysOperLog.getOperName());

        startPage();
        PageInfo<TSysOperLog> page = new PageInfo<TSysOperLog>(sysOperLogService.selectTSysOperLogList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }


    /**
     * 删除日志
     *
     * @param ids
     * @return
     */
    @Log(title = "删除日志", action = "remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("system:log:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysOperLogService.deleteTSysOperLogByIds(ids));
    }

    /**
     * 清空日志
     *
     * @return
     */
    @Log(title = "清空日志", action = "/LogController/clean")
    @ApiOperation(value = "清空日志", notes = "清空日志")
    @DeleteMapping("/clean")
    @RequiresPermissions("system:log:remove")
    @ResponseBody
    public AjaxResult clean() {
        sysOperLogService.cleanTSysOperLog();
        return success();
    }
}
