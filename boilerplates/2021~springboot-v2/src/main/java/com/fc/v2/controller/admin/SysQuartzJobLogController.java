package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysQuartzJobLog;
import com.fc.v2.service.ITSysQuartzJobLogService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务日志Controller
 *
 * @author fuce
 * @ClassName: QuartzJobLogController
 * @date 2019-11-20 22:51
 */
@Api(value = "定时任务调度日志表")
@Controller
@RequestMapping("/SysQuartzJobLogController")
public class SysQuartzJobLogController extends BaseController {

    private final String prefix = "admin/sysQuartzJobLog";

    @Autowired
    private ITSysQuartzJobLogService sysQuartzJobLogService;

    /**
     * 展示跳转页面
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:01:13
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysQuartzJobLog:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 定时任务调度日志list
     *
     * @param tSysQuartzJobLog
     * @return
     */
    @Log(title = "定时任务调度日志表集合查询", action = "list")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysQuartzJobLog:list")
    @ResponseBody
    public ResultTable list(TSysQuartzJobLog tSysQuartzJobLog) {
        QueryWrapper<TSysQuartzJobLog> queryWrapper = new QueryWrapper<TSysQuartzJobLog>();
        queryWrapper.like(StringUtils.isNotEmpty(tSysQuartzJobLog.getJobName()), "job_name", tSysQuartzJobLog.getJobName());
        queryWrapper.eq(StringUtils.isNotNull(tSysQuartzJobLog.getStatus()), "status", tSysQuartzJobLog.getStatus());

        startPage();
        PageInfo<TSysQuartzJobLog> page = new PageInfo<TSysQuartzJobLog>(sysQuartzJobLogService.selectTSysQuartzJobLogList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 查看详情
     *
     * @param modelMap
     * @return
     * @author fuce
     * @Date 2019年9月14日 下午11:50:42
     */
    @ApiOperation(value = "查看详情", notes = "查看详情")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap modelMap) {
        TSysQuartzJobLog log = sysQuartzJobLogService.selectTSysQuartzJobLogById(id);
        modelMap.put("SysQuartzJobLog", log);
        return prefix + "/detail";
    }


    /**
     * 定时任务日志删除
     *
     * @param ids id集合
     * @return
     * @author fuce
     * @Date 2019年11月20日 下午10:51:52
     */
    @Log(title = "定时任务调度日志表删除", action = "remove")
    @ApiOperation(value = "定时任务日志删除", notes = "定时任务日志删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysQuartzJobLog:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysQuartzJobLogService.deleteTSysQuartzJobLogByIds(ids));
    }

    /**
     * 清空日志
     *
     * @return
     */
    @Log(title = "清空日志", action = "clean")
    @ApiOperation(value = "清空日志", notes = "清空日志")
    @DeleteMapping("/clean")
    @RequiresPermissions("gen:sysQuartzJobLog:remove")
    @ResponseBody
    public AjaxResult clean() {
        sysQuartzJobLogService.cleanQuartzJobLog();
        return success();
    }
}
