
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.TSysStreetMapper;
import com.fc.v2.model.auto.TSysRoleUser;
import com.fc.v2.model.auto.TSysStreet;
import com.fc.v2.service.ITSysStreetService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 街道设置Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysStreetServiceImpl  extends ServiceImpl<TSysStreetMapper, TSysStreet> implements ITSysStreetService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysStreetServiceImpl.class);

    @Autowired
    private TSysStreetMapper tSysStreetMapper;

    /**
     * 查询街道设置
     * 
     * @param id 街道设置ID
     * @return 街道设置
     */
    @Override
    public TSysStreet selectTSysStreetById(Long id)
    {
        return this.baseMapper.selectById(id);
    }


    /**
     * 查询街道设置列表
     *
     * @param queryWrapper 街道设置
     * @return 街道设置
     */
    @Override
    public List<TSysStreet> selectTSysStreetList(Wrapper<TSysStreet> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询街道设置列表
     *
     * @param queryWrapper 街道设置
     * @return 街道设置
     */
    @Override
    public List<TSysStreet> selectTSysStreetList(TSysStreet tSysStreet) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysStreet, true, true);
    	QueryWrapper<TSysStreet> queryWrapper = new QueryWrapper<TSysStreet>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增街道设置
     * 
     * @param tSysStreet 街道设置
     * @return 结果
     */
    @Override
    public int insertTSysStreet(TSysStreet tSysStreet)
    {
        return this.baseMapper.insert(tSysStreet);
    }

    /**
     * 修改街道设置
     * 
     * @param tSysStreet 街道设置
     * @return 结果
     */
    @Override
    public int updateTSysStreet(TSysStreet tSysStreet)
    {
        return this.baseMapper.updateById(tSysStreet);
    }

    /**
     * 删除街道设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysStreetByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除街道设置信息
     * 
     * @param id 街道设置ID
     * @return 结果
     */
    @Override
    public int deleteTSysStreetById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查Name唯一
     *
     * @param sysStreet
     * @return
     */
    @Override
    public int checkNameUnique(TSysStreet sysStreet) {
        QueryWrapper<TSysStreet> queryWrapper = new QueryWrapper<TSysStreet>();
        queryWrapper.eq("street_name", sysStreet.getStreetName());
        return this.selectTSysStreetList(queryWrapper).size();
    }
}
