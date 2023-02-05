
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysEmail;

/**
 * 电子邮件Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysEmailService extends IService<TSysEmail> {
	/**
	 * 查询电子邮件
	 * 
	 * @param id 电子邮件ID
	 * @return 电子邮件
	 */
	public TSysEmail selectTSysEmailById(Long id);

	/**
	 * 查询电子邮件列表
	 * 
	 * @param queryWrapper 电子邮件
	 * @return 电子邮件集合
	 */
	public List<TSysEmail> selectTSysEmailList(Wrapper<TSysEmail> queryWrapper);

	/**
	 * 查询电子邮件列表
	 *
	 * @param tSysEmail 电子邮件
	 * @return 电子邮件集合
	 */
	public List<TSysEmail> selectTSysEmailList(TSysEmail tSysEmail);

	/**
	 * 新增电子邮件
	 * 
	 * @param tSysEmail 电子邮件
	 * @return 结果
	 */
	public int insertTSysEmail(TSysEmail tSysEmail);

	/**
	 * 修改电子邮件
	 * 
	 * @param tSysEmail 电子邮件
	 * @return 结果
	 */
	public int updateTSysEmail(TSysEmail tSysEmail);

	/**
	 * 批量删除电子邮件
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysEmailByIds(String ids);

	/**
	 * 删除电子邮件信息
	 * 
	 * @param id 电子邮件ID
	 * @return 结果
	 */
	public int deleteTSysEmailById(Long id);

	/**
	 * 检查name
	 *
	 * @param tSysEmail
	 * @return
	 */
	public int checkNameUnique(TSysEmail tSysEmail);
}
