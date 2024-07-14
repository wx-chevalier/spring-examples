
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.quartz.QuartzSchedulerUtil;
import com.fc.v2.common.quartz.ScheduleConstants;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysQuartzJobMapper;
import com.fc.v2.model.auto.TSysQuartzJob;
import com.fc.v2.service.ITSysQuartzJobService;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务调度Service业务层处理
 *
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysQuartzJobServiceImpl extends ServiceImpl<TSysQuartzJobMapper, TSysQuartzJob> implements ITSysQuartzJobService {
    private static final Logger logger = LoggerFactory.getLogger(TSysQuartzJobServiceImpl.class);

    @Autowired
    private TSysQuartzJobMapper tSysQuartzJobMapper;

    @Autowired
    private QuartzSchedulerUtil scheduler;

    /**
     * 查询定时任务调度
     *
     * @param id 定时任务调度ID
     * @return 定时任务调度
     */
    @Override
    public TSysQuartzJob selectTSysQuartzJobById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询定时任务调度列表
     *
     * @param queryWrapper 定时任务调度
     * @return 定时任务调度
     */
    @Override
    public List<TSysQuartzJob> selectTSysQuartzJobList(Wrapper<TSysQuartzJob> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增定时任务调度
     *
     * @param tSysQuartzJob 定时任务调度
     * @return 结果
     */
    @Override
    public int insertTSysQuartzJob(TSysQuartzJob tSysQuartzJob) {
        return this.baseMapper.insert(tSysQuartzJob);
    }

    /**
     * 修改定时任务调度
     *
     * @param tSysQuartzJob 定时任务调度
     * @return 结果
     */
    @Override
    public int updateTSysQuartzJob(TSysQuartzJob tSysQuartzJob) {
        return this.baseMapper.updateById(tSysQuartzJob);
    }

    /**
     * 删除定时任务调度对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysQuartzJobByIds(String ids) {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除定时任务调度信息
     *
     * @param id 定时任务调度ID
     * @return 结果
     */
    @Override
    public int deleteTSysQuartzJobById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 任务调度状态修改
     *
     * @param job
     * @return
     */
    @Override
    public int changeStatus(TSysQuartzJob job) throws SchedulerException {
        int rows = 0;
        Integer status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(TSysQuartzJob job) throws SchedulerException {
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = this.baseMapper.updateById(job);
        if (rows > 0) {
            scheduler.resumeJob(job);
        }
        return rows;
    }

    /**
     * 暂停任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(TSysQuartzJob job) throws SchedulerException {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = this.baseMapper.updateById(job);
        if (rows > 0) {
            scheduler.pauseJob(job);
        }
        return rows;
    }

    /**
     * 检查name
     *
     * @param sysQuartzJob
     * @return
     */
    @Override
    public int checkNameUnique(TSysQuartzJob sysQuartzJob) {
        QueryWrapper<TSysQuartzJob> queryWrapper = new QueryWrapper<TSysQuartzJob>();
        queryWrapper.eq("job_name" , sysQuartzJob.getJobName());
        return this.baseMapper.selectList(queryWrapper).size();
    }

    /**
     * 立即运行任务
     *
     * @param newJob
     * @throws SchedulerException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(TSysQuartzJob newJob) throws SchedulerException {
        scheduler.run(newJob);
    }

    /**
     * 查询定时任务调度列表
     *
     * @param tSysQuartzJob 定时任务调度
     * @return 定时任务调度集合
     */
    @Override
    public List<TSysQuartzJob> selectTSysQuartzJobList(TSysQuartzJob tSysQuartzJob) {
        Map<String, Object> map = BeanUtil.beanToMap(tSysQuartzJob, true, true);
        QueryWrapper<TSysQuartzJob> queryWrapper = new QueryWrapper<TSysQuartzJob>();
        queryWrapper.allEq(map, false);
        return this.baseMapper.selectList(queryWrapper);
    }
}
