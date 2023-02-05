
package com.fc.v2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.model.auto.TSysNoticeUser;
import com.fc.v2.model.auto.TSysUser;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.service.ITSysNoticeUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysNoticeMapper;
import com.fc.v2.model.auto.TSysNotice;
import com.fc.v2.service.ITSysNoticeService;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 公告Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysNoticeServiceImpl  extends ServiceImpl<TSysNoticeMapper, TSysNotice> implements ITSysNoticeService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysNoticeServiceImpl.class);

    @Autowired
    private TSysNoticeMapper tSysNoticeMapper;

    @Autowired
    private ITSysNoticeUserService userService;
    /**
     * 查询公告
     * 
     * @param id 公告ID
     * @return 公告
     */
    @Override
    public TSysNotice selectTSysNoticeById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询公告列表
     * 
     * @param queryWrapper 公告
     * @return 公告
     */
    @Override
    public List<TSysNotice> selectTSysNoticeList(Wrapper<TSysNotice> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询公告列表
     * 
     * @param tSysNotice 公告
     * @return 公告
     */
	@Override
	public List<TSysNotice> selectTSysNoticeList(TSysNotice tSysNotice) {
		Map<String, Object>  map = BeanUtil.beanToMap(tSysNotice, true, true);
    	QueryWrapper<TSysNotice> queryWrapper = new QueryWrapper<TSysNotice>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
	}

    /**
     * 查询公告列表
     *
     * @param tsysUser
     * @param tablepar
     * @param name
     * @return
     */
    @Override
    public PageInfo<TSysNotice> selectTSysNoticeList(TSysUser tsysUser, Tablepar tablepar, String name) {
        //查询未阅读的公告用户外键
        QueryWrapper<TSysNoticeUser> queryWrapper = new QueryWrapper<TSysNoticeUser>();
        queryWrapper.eq("user_id", tsysUser.getId());
        List<TSysNoticeUser> noticeUsers = userService.selectTSysNoticeUserList(queryWrapper);

        if (noticeUsers == null && noticeUsers.size() == 0){
            return new PageInfo<TSysNotice>();
        }

        //查询对应的公告列表
        List<Long> ids = new ArrayList<Long>();
        for (TSysNoticeUser sysNoticeUser : noticeUsers) {
            ids.add(sysNoticeUser.getNoticeId());
        }

        //分页查询对应用户的所有公告信息
        QueryWrapper<TSysNotice> sysNoticeQueryWrapper = new QueryWrapper<TSysNotice>();
        sysNoticeQueryWrapper.orderByDesc("id");
        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());

        List<TSysNotice> tSysNotices = this.selectTSysNoticeList(sysNoticeQueryWrapper);

        return new PageInfo<TSysNotice>(tSysNotices);
    }

    /**
     * 新增公告
     * 
     * @param tSysNotice 公告
     * @return 结果
     */
    @Override
    public int insertTSysNotice(TSysNotice tSysNotice)
    {
        return this.baseMapper.insert(tSysNotice);
    }

    /**
     * 修改公告
     * 
     * @param tSysNotice 公告
     * @return 结果
     */
    @Override
    public int updateTSysNotice(TSysNotice tSysNotice)
    {
        return this.baseMapper.updateById(tSysNotice);
    }

    /**
     * 删除公告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysNoticeByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除公告信息
     * 
     * @param id 公告ID
     * @return 结果
     */
    @Override
    public int deleteTSysNoticeById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

	/**
	 * 检查name
	 *
	 * @param sysNotice
	 * @return
	 */
    @Override
    public int checkNameUnique(TSysNotice sysNotice) {
        QueryWrapper<TSysNotice> queryWrapper = new QueryWrapper<TSysNotice>();
        queryWrapper.eq("title", sysNotice.getTitle());
        return this.selectTSysNoticeList(queryWrapper).size();
    }

	/**
	 * 根据公告id把当前用户的公告置为以查看
	 *
	 * @param id
	 */
    @Override
    public void editUserState(Long id) {
        userService.selectTSysNoticeUserById(id);
    }

    /**
	 * 获取用户未阅读公告
	 *
	 * @param user
	 * @param state
	 * @return
	 */
    @Override
    public List<TSysNotice> getuserNoticeNotRead(TSysUser user, int state) {
        List<TSysNotice> notices=new ArrayList<TSysNotice>();
        //查询未阅读的公告用户外键
        QueryWrapper<TSysNoticeUser> queryWrapper = new QueryWrapper<TSysNoticeUser>();
        queryWrapper.eq("user_id", user.getId()).eq(-1 != state, "state", state);

        List<TSysNoticeUser> noticeUsers = userService.selectTSysNoticeUserList(queryWrapper);
        if (noticeUsers != null && noticeUsers.size() > 0){
            //查询对应的公告列表
            List<Long> ids = new ArrayList<Long>();
            for (TSysNoticeUser sysNoticeUser : noticeUsers) {
                ids.add(sysNoticeUser.getNoticeId());
            }

            notices = this.baseMapper.selectBatchIds(ids);
        }

        return notices;
    }

	/**
	 * 获取最新8条公告
	 *
	 * @return
	 */
    @Override
    public List<TSysNotice> getNEW() {
        QueryWrapper<TSysNotice> queryWrapper = new QueryWrapper<TSysNotice>();
        queryWrapper.orderByAsc("id");
        PageHelper.startPage(1, 8);
        return selectTSysNoticeList(queryWrapper);
    }
}
