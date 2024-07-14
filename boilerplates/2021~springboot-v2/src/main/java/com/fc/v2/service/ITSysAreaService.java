
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.v2.model.auto.TSysArea;

/**
 * 地区设置Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysAreaService extends IService<TSysArea> {
	/**
	 * 查询地区设置
	 * 
	 * @param id 地区设置ID
	 * @return 地区设置
	 */
	public TSysArea selectTSysAreaById(Long id);

	/**
	 * 查询地区设置列表
	 * 
	 * @param queryWrapper 地区设置
	 * @return 地区设置集合
	 */
	public List<TSysArea> selectTSysAreaList(Wrapper<TSysArea> queryWrapper);

	/**
	 * 查询地区设置列表
	 *
	 * @param tSysArea 地区设置
	 * @return 地区设置集合
	 */
	public List<TSysArea> selectTSysAreaList(TSysArea tSysArea);

	/**
	 * 新增地区设置
	 * 
	 * @param tSysArea 地区设置
	 * @return 结果
	 */
	public int insertTSysArea(TSysArea tSysArea);

	/**
	 * 修改地区设置
	 * 
	 * @param tSysArea 地区设置
	 * @return 结果
	 */
	public int updateTSysArea(TSysArea tSysArea);

	/**
	 * 批量删除地区设置
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysAreaByIds(String ids);

	/**
	 * 删除地区设置信息
	 * 
	 * @param id 地区设置ID
	 * @return 结果
	 */
	public int deleteTSysAreaById(Long id);

	/**
	 * 检查Name唯一
	 *
	 * @param sysArea
	 * @return
	 */
	public int checkNameUnique(TSysArea sysArea);
}
