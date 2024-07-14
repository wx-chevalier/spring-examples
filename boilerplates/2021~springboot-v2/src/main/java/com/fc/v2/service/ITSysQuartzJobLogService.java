
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysQuartzJobLog;

/**
 * 定时任务调度日志Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysQuartzJobLogService extends IService<TSysQuartzJobLog> {
	/**
	 * 查询定时任务调度日志
	 * 
	 * @param id 定时任务调度日志ID
	 * @return 定时任务调度日志
	 */
	public TSysQuartzJobLog selectTSysQuartzJobLogById(Long id);

	/**
	 * 查询定时任务调度日志列表
	 * 
	 * @param queryWrapper 定时任务调度日志
	 * @return 定时任务调度日志集合
	 */
	public List<TSysQuartzJobLog> selectTSysQuartzJobLogList(Wrapper<TSysQuartzJobLog> queryWrapper);

	/**
	 * 查询定时任务调度日志列表
	 *
	 * @param tSysQuartzJobLog 定时任务调度日志
	 * @return 定时任务调度日志集合
	 */
	public List<TSysQuartzJobLog> selectTSysQuartzJobLogList(TSysQuartzJobLog tSysQuartzJobLog);

	/**
	 * 新增定时任务调度日志
	 * 
	 * @param tSysQuartzJobLog 定时任务调度日志
	 * @return 结果
	 */
	public int insertTSysQuartzJobLog(TSysQuartzJobLog tSysQuartzJobLog);

	/**
	 * 修改定时任务调度日志
	 * 
	 * @param tSysQuartzJobLog 定时任务调度日志
	 * @return 结果
	 */
	public int updateTSysQuartzJobLog(TSysQuartzJobLog tSysQuartzJobLog);

	/**
	 * 批量删除定时任务调度日志
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysQuartzJobLogByIds(String ids);

	/**
	 * 删除定时任务调度日志信息
	 * 
	 * @param id 定时任务调度日志ID
	 * @return 结果
	 */
	public int deleteTSysQuartzJobLogById(Long id);

	/**
	 * 清空定时任务调度日志信息
	 */
	public void cleanQuartzJobLog();
}
