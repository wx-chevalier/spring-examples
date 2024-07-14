package com.fc.v2.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.common.log.Log;
import com.fc.v2.model.auto.TSysQuartzJob;
import com.fc.v2.service.ITSysQuartzJobService;
import com.fc.v2.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jan 橙寂
 * @ClassName: QuartzJobController
 * @date 2019-11-20 22:49
 */
@Api(value = "定时任务调度表")
@Controller
@RequestMapping("/SysQuartzJobController")
public class SysQuartzJobController extends BaseController {

    private final String prefix = "admin/sysQuartzJob";

    @Autowired
    private ITSysQuartzJobService sysQuartzJobService;

    /**
     * 展示页面
     *
     * @param model
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午3:55:01
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @RequiresPermissions("gen:sysQuartzJob:view")
    public String view(ModelMap model) {
        return prefix + "/list";
    }

    /**
     * 定时任务调度list
     *
     * @param tSysQuartzJob
     * @return
     */
    @Log(title = "定时任务调度表集合查询", action = "list")
    @ApiOperation(value = "定时任务调度list", notes = "定时任务调度list")
    @GetMapping("/list")
    @RequiresPermissions("gen:sysQuartzJob:list")
    @ResponseBody
    public ResultTable list(TSysQuartzJob tSysQuartzJob) {
        QueryWrapper<TSysQuartzJob> queryWrapper = new QueryWrapper<TSysQuartzJob>();
        queryWrapper.like(StringUtils.isNotEmpty(tSysQuartzJob.getJobName()), "job_name", tSysQuartzJob.getJobName());
        queryWrapper.eq(StringUtils.isNotNull(tSysQuartzJob.getStatus()), "status", tSysQuartzJob.getStatus());

        startPage();
        PageInfo<TSysQuartzJob> page = new PageInfo<TSysQuartzJob>(sysQuartzJobService.selectTSysQuartzJobList(queryWrapper));
        return pageTable(page.getList(), page.getTotal());
    }

    /**
     * 新增跳转页面
     *
     * @param modelMap
     * @return
     */
    @ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        return prefix + "/add";
    }

    /**
     * 新增保存
     *
     * @param sysQuartzJob
     * @return
     * @author fuce
     * @Date 2019年11月11日 下午4:00:08
     */
    @Log(title = "定时任务调度表新增", action = "add")
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RequiresPermissions("gen:sysQuartzJob:add")
    @ResponseBody
    public AjaxResult add(TSysQuartzJob sysQuartzJob) {
        return toAjax(sysQuartzJobService.insertTSysQuartzJob(sysQuartzJob));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Log(title = "定时任务调度表删除", action = "/remove")
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping("/remove")
    @RequiresPermissions("gen:sysQuartzJob:remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysQuartzJobService.deleteTSysQuartzJobByIds(ids));
    }

    /**
     * 检查
     *
     * @param sysQuartzJob
     * @return
     */
    @ApiOperation(value = "检查Name唯一", notes = "检查Name唯一")
    @PostMapping("/checkNameUnique")
    @ResponseBody
    public int checkNameUnique(TSysQuartzJob sysQuartzJob) {
        return sysQuartzJobService.checkNameUnique(sysQuartzJob) > 0 ? 1 : 0;
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
        mmap.put("SysQuartzJob", sysQuartzJobService.selectTSysQuartzJobById(id));
        return prefix + "/edit";
    }

    /**
     * 修改保存
     *
     * @param record
     * @return
     */
    @Log(title = "定时任务调度表修改", action = "edit")
    @ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:sysQuartzJob:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TSysQuartzJob record) {
        return toAjax(sysQuartzJobService.updateTSysQuartzJob(record));
    }

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    @Log(title = "任务调度状态修改", action = "changeStatus")
    @ApiOperation(value = "任务调度状态修改", notes = "任务调度状态修改")
    @PutMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(@RequestBody TSysQuartzJob job) throws SchedulerException {
        TSysQuartzJob newJob = sysQuartzJobService.selectTSysQuartzJobById(job.getId());
        newJob.setStatus(job.getStatus());
        sysQuartzJobService.updateTSysQuartzJob(newJob);
        return toAjax(sysQuartzJobService.changeStatus(newJob));
    }

    /**
     * 任务调度立即执行一次
     *
     * @param id
     * @return
     * @throws SchedulerException
     */
    @Log(title = "任务调度立即执行一次", action = "run")
    @ApiOperation(value = "任务调度立即执行一次", notes = "任务调度立即执行一次")
    @GetMapping("/run/{id}")
    @ResponseBody
    public AjaxResult run(@PathVariable("id") Long id) throws SchedulerException {
        TSysQuartzJob newJob = sysQuartzJobService.selectTSysQuartzJobById(id);
        sysQuartzJobService.run(newJob);
        return success();
    }
}
