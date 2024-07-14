
package com.fc.v2.service;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.fc.v2.model.auto.TSysNoticeUser;

/**
 * 公告_用户外键Service接口
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
public interface ITSysNoticeUserService extends IService<TSysNoticeUser> {
	/**
	 * 查询公告_用户外键
	 * 
	 * @param id 公告_用户外键ID
	 * @return 公告_用户外键
	 */
	public TSysNoticeUser selectTSysNoticeUserById(Long id);

	/**
	 * 查询公告_用户外键列表
	 * 
	 * @param queryWrapper 公告_用户外键
	 * @return 公告_用户外键集合
	 */
	public List<TSysNoticeUser> selectTSysNoticeUserList(Wrapper<TSysNoticeUser> queryWrapper);

	/**
	 * 查询公告_用户外键列表
	 *
	 * @param tSysNoticeUser 公告_用户外键
	 * @return 公告_用户外键集合
	 */
	public List<TSysNoticeUser> selectTSysNoticeUserList(TSysNoticeUser tSysNoticeUser);

	/**
	 * 新增公告_用户外键
	 * 
	 * @param tSysNoticeUser 公告_用户外键
	 * @return 结果
	 */
	public int insertTSysNoticeUser(TSysNoticeUser tSysNoticeUser);

	/**
	 * 修改公告_用户外键
	 * 
	 * @param tSysNoticeUser 公告_用户外键
	 * @return 结果
	 */
	public int updateTSysNoticeUser(TSysNoticeUser tSysNoticeUser);

	/**
	 * 批量删除公告_用户外键
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteTSysNoticeUserByIds(String ids);

	/**
	 * 删除公告_用户外键信息
	 * 
	 * @param id 公告_用户外键ID
	 * @return 结果
	 */
	public int deleteTSysNoticeUserById(Long id);
}
