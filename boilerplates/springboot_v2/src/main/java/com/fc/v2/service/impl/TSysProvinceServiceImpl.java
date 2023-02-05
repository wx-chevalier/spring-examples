
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.TSysProvinceMapper;
import com.fc.v2.model.auto.TSysProvince;
import com.fc.v2.service.ITSysProvinceService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 省份Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysProvinceServiceImpl  extends ServiceImpl<TSysProvinceMapper, TSysProvince> implements ITSysProvinceService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysProvinceServiceImpl.class);

    @Autowired
    private TSysProvinceMapper tSysProvinceMapper;

    /**
     * 查询省份
     * 
     * @param id 省份ID
     * @return 省份
     */
    @Override
    public TSysProvince selectTSysProvinceById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询省份列表
     *
     * @param queryWrapper 省份
     * @return 省份
     */
    @Override
    public List<TSysProvince> selectTSysProvinceList(Wrapper<TSysProvince> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询省份列表
     *
     * @param tSysProvince 省份
     * @return 省份
     */
    @Override
    public List<TSysProvince> selectTSysProvinceList(TSysProvince tSysProvince) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysProvince, true, true);
    	QueryWrapper<TSysProvince> queryWrapper = new QueryWrapper<TSysProvince>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增省份
     * 
     * @param tSysProvince 省份
     * @return 结果
     */
    @Override
    public int insertTSysProvince(TSysProvince tSysProvince)
    {
        return this.baseMapper.insert(tSysProvince);
    }

    /**
     * 修改省份
     * 
     * @param tSysProvince 省份
     * @return 结果
     */
    @Override
    public int updateTSysProvince(TSysProvince tSysProvince)
    {
        return this.baseMapper.updateById(tSysProvince);
    }

    /**
     * 删除省份对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysProvinceByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除省份信息
     * 
     * @param id 省份ID
     * @return 结果
     */
    @Override
    public int deleteTSysProvinceById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查Name唯一
     *
     * @param sysCity
     * @return
     */
    @Override
    public int checkNameUnique(TSysProvince sysProvince) {
        QueryWrapper<TSysProvince> queryWrapper = new QueryWrapper<TSysProvince>();
        queryWrapper.eq("province_name", sysProvince.getProvinceName());
        return this.selectTSysProvinceList(queryWrapper).size();
    }
}
