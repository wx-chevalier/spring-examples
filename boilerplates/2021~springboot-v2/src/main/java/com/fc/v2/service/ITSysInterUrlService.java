
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysInterUrl;

/**
 * 拦截urlService接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysInterUrlService extends IService<TSysInterUrl> {
	/**
	 * 查询拦截url
	 * 
	 * @param id 拦截urlID
	 * @return 拦截url
	 */
	public TSysInterUrl selectTSysInterUrlById(Long id);

	/**
	 * 查询拦截url列表
	 * 
	 * @param queryWrapper 拦截url
	 * @return 拦截url集合
	 */
	public List<TSysInterUrl> selectTSysInterUrlList(Wrapper<TSysInterUrl> queryWrapper);

	/**
	 * 查询拦截url列表
	 *
	 * @param tSysInterUrl 拦截url
	 * @return 拦截url集合
	 */
	public List<TSysInterUrl> selectTSysInterUrlList(TSysInterUrl tSysInterUrl);

	/**
	 * 新增拦截url
	 * 
	 * @param tSysInterUrl 拦截url
	 * @return 结果
	 */
	public int insertTSysInterUrl(TSysInterUrl tSysInterUrl);

	/**
	 * 修改拦截url
	 * 
	 * @param tSysInterUrl 拦截url
	 * @return 结果
	 */
	public int updateTSysInterUrl(TSysInterUrl tSysInterUrl);

	/**
	 * 批量删除拦截url
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysInterUrlByIds(String ids);

	/**
	 * 删除拦截url信息
	 * 
	 * @param id 拦截urlID
	 * @return 结果
	 */
	public int deleteTSysInterUrlById(Long id);

	/**
	 * 检查name
	 *
	 * @param sysInterUrl
	 * @return
	 */
	public int checkNameUnique(TSysInterUrl sysInterUrl);
}
