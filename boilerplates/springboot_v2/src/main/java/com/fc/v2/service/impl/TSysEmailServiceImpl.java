
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysEmailMapper;
import com.fc.v2.model.auto.TSysEmail;
import com.fc.v2.service.ITSysEmailService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 电子邮件Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysEmailServiceImpl  extends ServiceImpl<TSysEmailMapper, TSysEmail> implements ITSysEmailService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysEmailServiceImpl.class);

    @Autowired
    private TSysEmailMapper tSysEmailMapper;

    /**
     * 查询电子邮件
     * 
     * @param id 电子邮件ID
     * @return 电子邮件
     */
    @Override
    public TSysEmail selectTSysEmailById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询电子邮件列表
     * 
     * @param queryWrapper 电子邮件
     * @return 电子邮件
     */
    @Override
    public List<TSysEmail> selectTSysEmailList(Wrapper<TSysEmail> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询电子邮件列表
     *
     * @param tSysEmail 电子邮件
     * @return 电子邮件
     */
    @Override
    public List<TSysEmail> selectTSysEmailList(TSysEmail tSysEmail) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysEmail, true, true);
    	QueryWrapper<TSysEmail> queryWrapper = new QueryWrapper<TSysEmail>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增电子邮件
     * 
     * @param tSysEmail 电子邮件
     * @return 结果
     */
    @Override
    public int insertTSysEmail(TSysEmail tSysEmail)
    {
        return this.baseMapper.insert(tSysEmail);
    }

    /**
     * 修改电子邮件
     * 
     * @param tSysEmail 电子邮件
     * @return 结果
     */
    @Override
    public int updateTSysEmail(TSysEmail tSysEmail)
    {
        return this.baseMapper.updateById(tSysEmail);
    }

    /**
     * 删除电子邮件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysEmailByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除电子邮件信息
     * 
     * @param id 电子邮件ID
     * @return 结果
     */
    @Override
    public int deleteTSysEmailById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查name
     *
     * @param tSysEmail
     * @return
     */
    @Override
    public int checkNameUnique(TSysEmail tSysEmail) {
        QueryWrapper<TSysEmail> queryWrapper = new QueryWrapper<TSysEmail>();
        queryWrapper.eq("content", tSysEmail.getContent());
        return this.baseMapper.selectList(queryWrapper).size();
    }
}
