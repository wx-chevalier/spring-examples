
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysDepartment;

/**
 * 部门Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysDepartmentService extends IService<TSysDepartment> {
	/**
	 * 查询部门
	 * 
	 * @param id 部门ID
	 * @return 部门
	 */
	public TSysDepartment selectTSysDepartmentById(Long id);

	/**
	 * 查询部门列表
	 * 
	 * @param queryWrapper 部门
	 * @return 部门集合
	 */
	public List<TSysDepartment> selectTSysDepartmentList(Wrapper<TSysDepartment> queryWrapper);

	/**
	 * 查询部门列表
	 *
	 * @param tSysDepartment 部门
	 * @return 部门集合
	 */
	public List<TSysDepartment> selectTSysDepartmentList(TSysDepartment tSysDepartment);

	/**
	 * 新增部门
	 * 
	 * @param tSysDepartment 部门
	 * @return 结果
	 */
	public int insertTSysDepartment(TSysDepartment tSysDepartment);

	/**
	 * 修改部门
	 * 
	 * @param tSysDepartment 部门
	 * @return 结果
	 */
	public int updateTSysDepartment(TSysDepartment tSysDepartment);

	/**
	 * 批量删除部门
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysDepartmentByIds(String ids);

	/**
	 * 删除部门信息
	 * 
	 * @param id 部门ID
	 * @return 结果
	 */
	public int deleteTSysDepartmentById(Long id);

	/**
	 * 检查name
	 *
	 * @param sysDepartment
	 * @return
	 */
	int checkNameUnique(TSysDepartment sysDepartment);

	/**
	 * 修改权限状态展示或者不展示
	 *
	 * @param record
	 * @return
	 */
	int updateVisible(TSysDepartment record);
}
