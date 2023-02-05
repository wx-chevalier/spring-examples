
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.v2.model.auto.TSysCity;

/**
 * 城市设置Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysCityService extends IService<TSysCity> {
	/**
	 * 查询城市设置
	 * 
	 * @param id 城市设置ID
	 * @return 城市设置
	 */
	public TSysCity selectTSysCityById(Long id);

	/**
	 * 查询城市设置列表
	 * 
	 * @param queryWrapper 城市设置
	 * @return 城市设置集合
	 */
	public List<TSysCity> selectTSysCityList(Wrapper<TSysCity> queryWrapper);

	/**
	 * 查询城市设置列表
	 *
	 * @param tSysCity 城市设置
	 * @return 城市设置集合
	 */
	public List<TSysCity> selectTSysCityList(TSysCity tSysCity);

	/**
	 * 新增城市设置
	 * 
	 * @param tSysCity 城市设置
	 * @return 结果
	 */
	public int insertTSysCity(TSysCity tSysCity);

	/**
	 * 修改城市设置
	 * 
	 * @param tSysCity 城市设置
	 * @return 结果
	 */
	public int updateTSysCity(TSysCity tSysCity);

	/**
	 * 批量删除城市设置
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysCityByIds(String ids);

	/**
	 * 删除城市设置信息
	 * 
	 * @param id 城市设置ID
	 * @return 结果
	 */
	public int deleteTSysCityById(Long id);

	/**
	 * 检查Name唯一
	 *
	 * @param sysCity
	 * @return
	 */
	int checkNameUnique(TSysCity sysCity);
}
