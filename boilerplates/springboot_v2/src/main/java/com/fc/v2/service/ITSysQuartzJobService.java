
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysQuartzJob;
import org.quartz.SchedulerException;

/**
 * 定时任务调度Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysQuartzJobService extends IService<TSysQuartzJob> {
	/**
	 * 查询定时任务调度
	 * 
	 * @param id 定时任务调度ID
	 * @return 定时任务调度
	 */
	public TSysQuartzJob selectTSysQuartzJobById(Long id);

	/**
	 * 查询定时任务调度列表
	 * 
	 * @param queryWrapper 定时任务调度
	 * @return 定时任务调度集合
	 */
	public List<TSysQuartzJob> selectTSysQuartzJobList(Wrapper<TSysQuartzJob> queryWrapper);

	/**
	 * 查询定时任务调度列表
	 *
	 * @param tSysQuartzJob 定时任务调度
	 * @return 定时任务调度集合
	 */
	public List<TSysQuartzJob> selectTSysQuartzJobList(TSysQuartzJob tSysQuartzJob);

	/**
	 * 新增定时任务调度
	 * 
	 * @param tSysQuartzJob 定时任务调度
	 * @return 结果
	 */
	public int insertTSysQuartzJob(TSysQuartzJob tSysQuartzJob);

	/**
	 * 修改定时任务调度
	 * 
	 * @param tSysQuartzJob 定时任务调度
	 * @return 结果
	 */
	public int updateTSysQuartzJob(TSysQuartzJob tSysQuartzJob);

	/**
	 * 批量删除定时任务调度
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysQuartzJobByIds(String ids);

	/**
	 * 删除定时任务调度信息
	 * 
	 * @param id 定时任务调度ID
	 * @return 结果
	 */
	public int deleteTSysQuartzJobById(Long id);

	/**
	 * 任务调度状态修改
	 *
	 * @param newJob
	 * @return
	 */
	public int changeStatus(TSysQuartzJob newJob) throws SchedulerException;

	/**
	 * 恢复任务
	 *
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public int resumeJob(TSysQuartzJob job) throws SchedulerException;

	/**
	 * 暂停任务
	 *
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	public int pauseJob(TSysQuartzJob job) throws SchedulerException;
	/**
	 * 检查name
	 *
	 * @param sysQuartzJob
	 * @return
	 */
	public int checkNameUnique(TSysQuartzJob sysQuartzJob);

	/**
	 * 立即运行任务
	 *
	 * @param newJob
	 * @throws SchedulerException
	 */
	public void run(TSysQuartzJob newJob) throws SchedulerException;
}
