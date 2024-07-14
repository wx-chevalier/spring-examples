
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.TSysCityMapper;
import com.fc.v2.model.auto.TSysCity;
import com.fc.v2.service.ITSysCityService;

import cn.hutool.core.bean.BeanUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 城市设置Service业务层处理
 *
 * @author zhaonz
 * @date 2021-08-05
 */
@Service
public class TSysCityServiceImpl extends ServiceImpl<TSysCityMapper, TSysCity> implements ITSysCityService {
	
    private static final Logger logger = LoggerFactory.getLogger(TSysCityServiceImpl.class);

    @Autowired
    private TSysCityMapper tSysCityMapper;

    /**
     * 查询城市设置
     *
     * @param id 城市设置ID
     * @return 城市设置
     */
    @Override
    public TSysCity selectTSysCityById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询城市设置列表
     *
     * @param queryWrapper 城市设置
     * @return 城市设置
     */
    @Override
    public List<TSysCity> selectTSysCityList(Wrapper<TSysCity> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询城市设置列表
     *
     * @param tSysCity 城市设置
     * @return 城市设置
     */
    @Override
    public List<TSysCity> selectTSysCityList(TSysCity tSysCity) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysCity, true, true);
    	QueryWrapper<TSysCity> queryWrapper = new QueryWrapper<TSysCity>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增城市设置
     *
     * @param tSysCity 城市设置
     * @return 结果
     */
    @Override
    public int insertTSysCity(TSysCity tSysCity) {
        return this.baseMapper.insert(tSysCity);
    }

    /**
     * 修改城市设置
     *
     * @param tSysCity 城市设置
     * @return 结果
     */
    @Override
    public int updateTSysCity(TSysCity tSysCity) {
        return this.baseMapper.updateById(tSysCity);
    }

    /**
     * 删除城市设置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysCityByIds(String ids) {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除城市设置信息
     *
     * @param id 城市设置ID
     * @return 结果
     */
    @Override
    public int deleteTSysCityById(Long id) {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查Name唯一
     *
     * @param sysCity
     * @return
     */
    @Override
    public int checkNameUnique(TSysCity sysCity) {
        QueryWrapper<TSysCity> queryWrapper = new QueryWrapper<TSysCity>();
        queryWrapper.eq("city_name", sysCity.getCityName());
        return this.selectTSysCityList(queryWrapper).size();
    }
}
