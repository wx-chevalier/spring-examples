
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysOperLog;

/**
 * 日志记录Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysOperLogService extends IService<TSysOperLog> {
	/**
	 * 查询日志记录
	 * 
	 * @param id 日志记录ID
	 * @return 日志记录
	 */
	public TSysOperLog selectTSysOperLogById(Long id);

	/**
	 * 查询日志记录列表
	 * 
	 * @param queryWrapper 日志记录
	 * @return 日志记录集合
	 */
	public List<TSysOperLog> selectTSysOperLogList(Wrapper<TSysOperLog> queryWrapper);

	/**
	 * 查询日志记录列表
	 *
	 * @param tSysOperLog 日志记录
	 * @return 日志记录集合
	 */
	public List<TSysOperLog> selectTSysOperLogList(TSysOperLog tSysOperLog);

	/**
	 * 新增日志记录
	 * 
	 * @param tSysOperLog 日志记录
	 * @return 结果
	 */
	public int insertTSysOperLog(TSysOperLog tSysOperLog);

	/**
	 * 修改日志记录
	 * 
	 * @param tSysOperLog 日志记录
	 * @return 结果
	 */
	public int updateTSysOperLog(TSysOperLog tSysOperLog);

	/**
	 * 批量删除日志记录
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysOperLogByIds(String ids);

	/**
	 * 删除日志记录信息
	 * 
	 * @param id 日志记录ID
	 * @return 结果
	 */
	public int deleteTSysOperLogById(Long id);

	/**
	 * 获取最新10条日志
	 *
	 * @return
	 */
	public List<TSysOperLog> getNEW();

	/**
	 * 清空日志
	 *
	 * @return
	 */
	public void cleanTSysOperLog();
}
