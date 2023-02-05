
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysDictType;

/**
 * 字典类型Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysDictTypeService extends IService<TSysDictType> {
	/**
	 * 查询字典类型
	 * 
	 * @param id 字典类型ID
	 * @return 字典类型
	 */
	public TSysDictType selectTSysDictTypeById(Long id);

	/**
	 * 查询字典类型列表
	 * 
	 * @param queryWrapper 字典类型
	 * @return 字典类型集合
	 */
	public List<TSysDictType> selectTSysDictTypeList(Wrapper<TSysDictType> queryWrapper);

	/**
	 * 查询字典类型列表
	 *
	 * @param tSysDictType 字典类型
	 * @return 字典类型集合
	 */
	public List<TSysDictType> selectTSysDictTypeList(TSysDictType tSysDictType);

	/**
	 * 新增字典类型
	 * 
	 * @param tSysDictType 字典类型
	 * @return 结果
	 */
	public int insertTSysDictType(TSysDictType tSysDictType);

	/**
	 * 修改字典类型
	 * 
	 * @param tSysDictType 字典类型
	 * @return 结果
	 */
	public int updateTSysDictType(TSysDictType tSysDictType);

	/**
	 * 批量删除字典类型
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysDictTypeByIds(String ids);

	/**
	 * 删除字典类型信息
	 * 
	 * @param id 字典类型ID
	 * @return 结果
	 */
	public int deleteTSysDictTypeById(Long id);

	/**
	 * 检查name
	 *
	 * @param tSysDictType
	 * @return
	 */
	public int checkNameUnique(TSysDictType tSysDictType);
}
