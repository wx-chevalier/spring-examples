
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.service.ITSysPositionService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysPositionMapper;
import com.fc.v2.model.auto.TSysPermissionRole;
import com.fc.v2.model.auto.TSysPosition;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 岗位Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysPositionServiceImpl  extends ServiceImpl<TSysPositionMapper, TSysPosition> implements ITSysPositionService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysPositionServiceImpl.class);

    @Autowired
    private TSysPositionMapper tSysPositionMapper;

    /**
     * 查询岗位
     * 
     * @param id 岗位ID
     * @return 岗位
     */
    @Override
    public TSysPosition selectTSysPositionById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询岗位列表
     * 
     * @param queryWrapper 岗位
     * @return 岗位
     */
    @Override
    public List<TSysPosition> selectTSysPositionList(Wrapper<TSysPosition> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TSysPosition> selectTSysPositionList(TSysPosition tSysPosition) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysPosition, true, true);
    	QueryWrapper<TSysPosition> queryWrapper = new QueryWrapper<TSysPosition>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增岗位
     * 
     * @param tSysPosition 岗位
     * @return 结果
     */
    @Override
    public int insertTSysPosition(TSysPosition tSysPosition)
    {
        return this.baseMapper.insert(tSysPosition);
    }

    /**
     * 修改岗位
     * 
     * @param tSysPosition 岗位
     * @return 结果
     */
    @Override
    public int updateTSysPosition(TSysPosition tSysPosition)
    {
        return this.baseMapper.updateById(tSysPosition);
    }

    /**
     * 删除岗位对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysPositionByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除岗位信息
     * 
     * @param id 岗位ID
     * @return 结果
     */
    @Override
    public int deleteTSysPositionById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     *
     * @param sysPosition
     * @return
     */
    @Override
    public int checkNameUnique(TSysPosition sysPosition) {
        QueryWrapper<TSysPosition> queryWrapper = new QueryWrapper<TSysPosition>();
        queryWrapper.eq("post_name", sysPosition.getPostName());
        return this.baseMapper.selectList(queryWrapper).size();
    }

    /**
     *
     * @param record
     * @return
     */
    @Override
    public int updateVisible(TSysPosition record) {
        return this.baseMapper.updateById(record);
    }
}
