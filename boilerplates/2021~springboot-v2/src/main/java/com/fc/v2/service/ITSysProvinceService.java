
package com.fc.v2.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.v2.model.auto.TSysProvince;

/**
 * 省份Service接口
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITSysProvinceService extends IService<TSysProvince> {
	/**
	 * 查询省份
	 * 
	 * @param id 省份ID
	 * @return 省份
	 */
	public TSysProvince selectTSysProvinceById(Long id);

	/**
	 * 查询省份列表
	 * 
	 * @param queryWrapper 省份
	 * @return 省份集合
	 */
	public List<TSysProvince> selectTSysProvinceList(Wrapper<TSysProvince> queryWrapper);

	/**
	 * 查询省份列表
	 *
	 * @param tSysProvince 省份
	 * @return 省份集合
	 */
	public List<TSysProvince> selectTSysProvinceList(TSysProvince tSysProvince);

	/**
	 * 新增省份
	 * 
	 * @param tSysProvince 省份
	 * @return 结果
	 */
	public int insertTSysProvince(TSysProvince tSysProvince);

	/**
	 * 修改省份
	 * 
	 * @param tSysProvince 省份
	 * @return 结果
	 */
	public int updateTSysProvince(TSysProvince tSysProvince);

	/**
	 * 批量删除省份
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysProvinceByIds(String ids);

	/**
	 * 删除省份信息
	 * 
	 * @param id 省份ID
	 * @return 结果
	 */
	public int deleteTSysProvinceById(Long id);

	/**
	 * 检查Name唯一
	 *
	 * @param sysProvince
	 * @return
	 */
	int checkNameUnique(TSysProvince sysProvince);
}
