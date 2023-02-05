
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysDictData;

/**
 * 字典数据Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysDictDataService extends IService<TSysDictData> {
	/**
	 * 查询字典数据
	 * 
	 * @param id 字典数据ID
	 * @return 字典数据
	 */
	public TSysDictData selectTSysDictDataById(Long id);

	/**
	 * 查询字典数据列表
	 * 
	 * @param queryWrapper 字典数据
	 * @return 字典数据集合
	 */
	public List<TSysDictData> selectTSysDictDataList(Wrapper<TSysDictData> queryWrapper);

	/**
	 * 查询字典数据列表
	 *
	 * @param tSysDictData 字典数据
	 * @return 字典数据集合
	 */
	public List<TSysDictData> selectTSysDictDataList(TSysDictData tSysDictData);

	/**
	 * 新增字典数据
	 * 
	 * @param tSysDictData 字典数据
	 * @return 结果
	 */
	public int insertTSysDictData(TSysDictData tSysDictData);

	/**
	 * 修改字典数据
	 * 
	 * @param tSysDictData 字典数据
	 * @return 结果
	 */
	public int updateTSysDictData(TSysDictData tSysDictData);

	/**
	 * 批量删除字典数据
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysDictDataByIds(String ids);

	/**
	 * 删除字典数据信息
	 * 
	 * @param id 字典数据ID
	 * @return 结果
	 */
	public int deleteTSysDictDataById(Long id);

	/**
	 * 删除字典数据信息
	 *
	 * @param queryWrapper
	 * @return
	 */
	public int deleteTSysDictData(Wrapper<TSysDictData> queryWrapper);

	/**
	 * 检查name
	 *
	 * @param tSysDictData
	 * @return
	 */
	public int checkNameUnique(TSysDictData tSysDictData);
}
