
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysNoticeUserMapper;
import com.fc.v2.model.auto.TSysNoticeUser;
import com.fc.v2.service.ITSysNoticeUserService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 公告_用户外键Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysNoticeUserServiceImpl  extends ServiceImpl<TSysNoticeUserMapper, TSysNoticeUser> implements ITSysNoticeUserService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysNoticeUserServiceImpl.class);

    @Autowired
    private TSysNoticeUserMapper tSysNoticeUserMapper;

    /**
     * 查询公告_用户外键
     * 
     * @param id 公告_用户外键ID
     * @return 公告_用户外键
     */
    @Override
    public TSysNoticeUser selectTSysNoticeUserById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询公告_用户外键列表
     * 
     * @param queryWrapper 公告_用户外键
     * @return 公告_用户外键
     */
    @Override
    public List<TSysNoticeUser> selectTSysNoticeUserList(Wrapper<TSysNoticeUser> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询公告_用户外键列表
     *
     * @param tSysNoticeUser 公告_用户外键
     * @return 公告_用户外键
     */
    @Override
    public List<TSysNoticeUser> selectTSysNoticeUserList(TSysNoticeUser tSysNoticeUser) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysNoticeUser, true, true);
    	QueryWrapper<TSysNoticeUser> queryWrapper = new QueryWrapper<TSysNoticeUser>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增公告_用户外键
     * 
     * @param tSysNoticeUser 公告_用户外键
     * @return 结果
     */
    @Override
    public int insertTSysNoticeUser(TSysNoticeUser tSysNoticeUser)
    {
        return this.baseMapper.insert(tSysNoticeUser);
    }

    /**
     * 修改公告_用户外键
     * 
     * @param tSysNoticeUser 公告_用户外键
     * @return 结果
     */
    @Override
    public int updateTSysNoticeUser(TSysNoticeUser tSysNoticeUser)
    {
        return this.baseMapper.updateById(tSysNoticeUser);
    }

    /**
     * 删除公告_用户外键对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysNoticeUserByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除公告_用户外键信息
     * 
     * @param id 公告_用户外键ID
     * @return 结果
     */
    @Override
    public int deleteTSysNoticeUserById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }
}
