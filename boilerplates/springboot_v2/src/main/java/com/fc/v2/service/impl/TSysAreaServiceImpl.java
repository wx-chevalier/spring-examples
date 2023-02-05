
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.TSysAreaMapper;
import com.fc.v2.model.auto.TSysArea;
import com.fc.v2.service.ITSysAreaService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 地区设置Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysAreaServiceImpl  extends ServiceImpl<TSysAreaMapper, TSysArea> implements ITSysAreaService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysAreaServiceImpl.class);

    @Autowired
    private TSysAreaMapper tSysAreaMapper;

    /**
     * 查询地区设置
     * 
     * @param id 地区设置ID
     * @return 地区设置
     */
    @Override
    public TSysArea selectTSysAreaById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询地区设置列表
     * 
     * @param queryWrapper 地区设置
     * @return 地区设置
     */
    @Override
    public List<TSysArea> selectTSysAreaList(Wrapper<TSysArea> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询地区设置列表
     *
     * @param tSysArea 地区设置
     * @return 地区设置
     */
    @Override
    public List<TSysArea> selectTSysAreaList(TSysArea tSysArea) {
        Map<String, Object> map = BeanUtil.beanToMap(tSysArea, true, true);
        QueryWrapper<TSysArea> queryWrapper = new QueryWrapper<TSysArea>();
        queryWrapper.allEq(map, false);
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增地区设置
     * 
     * @param tSysArea 地区设置
     * @return 结果
     */
    @Override
    public int insertTSysArea(TSysArea tSysArea)
    {
        return this.baseMapper.insert(tSysArea);
    }

    /**
     * 修改地区设置
     * 
     * @param tSysArea 地区设置
     * @return 结果
     */
    @Override
    public int updateTSysArea(TSysArea tSysArea)
    {
        return this.baseMapper.updateById(tSysArea);
    }

    /**
     * 删除地区设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysAreaByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除地区设置信息
     * 
     * @param id 地区设置ID
     * @return 结果
     */
    @Override
    public int deleteTSysAreaById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查Name唯一
     *
     * @param sysArea
     * @return
     */
    @Override
    public int checkNameUnique(TSysArea sysArea) {
        QueryWrapper<TSysArea> queryWrapper = new QueryWrapper<TSysArea>();
        queryWrapper.eq("area_name", sysArea.getAreaName());
        return this.selectTSysAreaList(queryWrapper).size();
    }
}
