
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysPosition;

/**
 * 岗位Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysPositionService extends IService<TSysPosition> {
	/**
	 * 查询岗位
	 * 
	 * @param id 岗位ID
	 * @return 岗位
	 */
	public TSysPosition selectTSysPositionById(Long id);

	/**
	 * 查询岗位列表
	 * 
	 * @param queryWrapper 岗位
	 * @return 岗位集合
	 */
	public List<TSysPosition> selectTSysPositionList(Wrapper<TSysPosition> queryWrapper);

	/**
	 * 查询岗位列表
	 *
	 * @param tSysPosition 岗位
	 * @return 岗位集合
	 */
	public List<TSysPosition> selectTSysPositionList(TSysPosition tSysPosition);

	/**
	 * 新增岗位
	 * 
	 * @param tSysPosition 岗位
	 * @return 结果
	 */
	public int insertTSysPosition(TSysPosition tSysPosition);

	/**
	 * 修改岗位
	 * 
	 * @param tSysPosition 岗位
	 * @return 结果
	 */
	public int updateTSysPosition(TSysPosition tSysPosition);

	/**
	 * 批量删除岗位
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysPositionByIds(String ids);

	/**
	 * 删除岗位信息
	 * 
	 * @param id 岗位ID
	 * @return 结果
	 */
	public int deleteTSysPositionById(Long id);

	/**
	 * 检查name
	 *
	 * @param sysPosition
	 * @return
	 */
	int checkNameUnique(TSysPosition sysPosition);

	/**
	 * 修改权限状态展示或者不展示
	 *
	 * @param record
	 * @return
	 */
	int updateVisible(TSysPosition record);
}
