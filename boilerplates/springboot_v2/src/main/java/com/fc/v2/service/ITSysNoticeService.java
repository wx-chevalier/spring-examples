
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysNotice;
import com.fc.v2.model.auto.TSysUser;
import com.fc.v2.model.custom.Tablepar;
import com.github.pagehelper.PageInfo;

/**
 * 公告Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysNoticeService extends IService<TSysNotice> {
	/**
	 * 查询公告
	 * 
	 * @param id 公告ID
	 * @return 公告
	 */
	public TSysNotice selectTSysNoticeById(Long id);

	/**
	 * 查询公告列表
	 * 
	 * @param queryWrapper 公告
	 * @return 公告集合
	 */
	public List<TSysNotice> selectTSysNoticeList(Wrapper<TSysNotice> queryWrapper);

	/**
	 * 查询公告列表
	 *
	 * @param tSysNotice 公告
	 * @return 公告集合
	 */
	public List<TSysNotice> selectTSysNoticeList(TSysNotice tSysNotice);

	/**
	 * 查询公告列表
	 *
	 * @param tsysUser
	 * @param tablepar
	 * @param name
	 * @return
	 */
	public PageInfo<TSysNotice> selectTSysNoticeList(TSysUser tsysUser, Tablepar tablepar, String name);

	/**
	 * 新增公告
	 * 
	 * @param tSysNotice 公告
	 * @return 结果
	 */
	public int insertTSysNotice(TSysNotice tSysNotice);

	/**
	 * 修改公告
	 * 
	 * @param tSysNotice 公告
	 * @return 结果
	 */
	public int updateTSysNotice(TSysNotice tSysNotice);

	/**
	 * 批量删除公告
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysNoticeByIds(String ids);

	/**
	 * 删除公告信息
	 * 
	 * @param id 公告ID
	 * @return 结果
	 */
	public int deleteTSysNoticeById(Long id);

	/**
	 * 检查name
	 *
	 * @param sysNotice
	 * @return
	 */
	public int checkNameUnique(TSysNotice sysNotice);

	/**
	 * 根据公告id把当前用户的公告置为以查看
	 *
	 * @param id
	 */
	public void editUserState(Long id);

	/**
	 * 获取用户未阅读公告
	 *
	 * @param user
	 * @param state
	 * @return
	 */
	public List<TSysNotice> getuserNoticeNotRead(TSysUser user, int state);

	/**
	 * 获取最新8条公告
	 *
	 * @return
	 */
	public List<TSysNotice> getNEW();
}
