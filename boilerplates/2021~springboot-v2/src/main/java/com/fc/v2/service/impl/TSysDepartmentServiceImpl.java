
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.service.ITSysDepartmentService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysDepartmentMapper;
import com.fc.v2.model.auto.TSysDepartment;
import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 部门Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysDepartmentServiceImpl  extends ServiceImpl<TSysDepartmentMapper, TSysDepartment> implements ITSysDepartmentService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysDepartmentServiceImpl.class);

    @Autowired
    private TSysDepartmentMapper tSysDepartmentMapper;

    /**
     * 查询部门
     * 
     * @param id 部门ID
     * @return 部门
     */
    @Override
    public TSysDepartment selectTSysDepartmentById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询部门列表
     * 
     * @param queryWrapper 部门
     * @return 部门
     */
    @Override
    public List<TSysDepartment> selectTSysDepartmentList(Wrapper<TSysDepartment> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询部门列表
     *
     * @param tSysDepartment 部门
     * @return 部门
     */
    @Override
    public List<TSysDepartment> selectTSysDepartmentList(TSysDepartment tSysDepartment) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysDepartment, true, true);
    	QueryWrapper<TSysDepartment> queryWrapper = new QueryWrapper<TSysDepartment>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增部门
     * 
     * @param tSysDepartment 部门
     * @return 结果
     */
    @Override
    public int insertTSysDepartment(TSysDepartment tSysDepartment)
    {
        return this.baseMapper.insert(tSysDepartment);
    }

    /**
     * 修改部门
     * 
     * @param tSysDepartment 部门
     * @return 结果
     */
    @Override
    public int updateTSysDepartment(TSysDepartment tSysDepartment)
    {
        return this.baseMapper.updateById(tSysDepartment);
    }

    /**
     * 删除部门对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysDepartmentByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除部门信息
     * 
     * @param id 部门ID
     * @return 结果
     */
    @Override
    public int deleteTSysDepartmentById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     *
     * @param sysDepartment
     * @return
     */
    @Override
    public int checkNameUnique(TSysDepartment sysDepartment) {
        QueryWrapper<TSysDepartment> queryWrapper = new QueryWrapper<TSysDepartment>();
        queryWrapper.eq("dept_name", sysDepartment.getDeptName());
        return this.baseMapper.selectList(queryWrapper).size();
    }

    /**
     *
     * @param record
     * @return
     */
    @Override
    public int updateVisible(TSysDepartment record) {
        return this.baseMapper.updateById(record);
    }
}
