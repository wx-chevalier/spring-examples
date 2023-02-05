
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.v2.model.auto.TSysStreet;

/**
 * 街道设置Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysStreetService extends IService<TSysStreet> {
	/**
	 * 查询街道设置
	 * 
	 * @param id 街道设置ID
	 * @return 街道设置
	 */
	public TSysStreet selectTSysStreetById(Long id);

	/**
	 * 查询街道设置列表
	 * 
	 * @param queryWrapper 街道设置
	 * @return 街道设置集合
	 */
	public List<TSysStreet> selectTSysStreetList(Wrapper<TSysStreet> queryWrapper);

	/**
	 * 查询街道设置列表
	 *
	 * @param tSysStreet 街道设置
	 * @return 街道设置集合
	 */
	public List<TSysStreet> selectTSysStreetList(TSysStreet tSysStreet);

	/**
	 * 新增街道设置
	 * 
	 * @param tSysStreet 街道设置
	 * @return 结果
	 */
	public int insertTSysStreet(TSysStreet tSysStreet);

	/**
	 * 修改街道设置
	 * 
	 * @param tSysStreet 街道设置
	 * @return 结果
	 */
	public int updateTSysStreet(TSysStreet tSysStreet);

	/**
	 * 批量删除街道设置
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysStreetByIds(String ids);

	/**
	 * 删除街道设置信息
	 * 
	 * @param id 街道设置ID
	 * @return 结果
	 */
	public int deleteTSysStreetById(Long id);

	/**
	 * 检查Name唯一
	 *
	 * @param sysStreet
	 * @return
	 */
	int checkNameUnique(TSysStreet sysStreet);
}
