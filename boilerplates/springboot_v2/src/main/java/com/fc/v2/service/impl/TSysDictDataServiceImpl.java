
package com.fc.v2.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysDictDataMapper;
import com.fc.v2.model.auto.TSysDictData;
import com.fc.v2.service.ITSysDictDataService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 字典数据Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysDictDataServiceImpl  extends ServiceImpl<TSysDictDataMapper, TSysDictData> implements ITSysDictDataService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysDictDataServiceImpl.class);

    @Autowired
    private TSysDictDataMapper tSysDictDataMapper;

    /**
     * 查询字典数据
     * 
     * @param id 字典数据ID
     * @return 字典数据
     */
    @Override
    public TSysDictData selectTSysDictDataById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询字典数据列表
     * 
     * @param queryWrapper 字典数据
     * @return 字典数据
     */
    @Override
    public List<TSysDictData> selectTSysDictDataList(Wrapper<TSysDictData> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询字典数据列表
     *
     * @param tSysDictData 字典数据
     * @return 字典数据
     */
    @Override
    public List<TSysDictData> selectTSysDictDataList(TSysDictData tSysDictData) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysDictData, true, true);
    	QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增字典数据
     * 
     * @param tSysDictData 字典数据
     * @return 结果
     */
    @Override
    public int insertTSysDictData(TSysDictData tSysDictData)
    {
        return this.baseMapper.insert(tSysDictData);
    }

    /**
     * 修改字典数据
     * 
     * @param tSysDictData 字典数据
     * @return 结果
     */
    @Override
    public int updateTSysDictData(TSysDictData tSysDictData)
    {
        return this.baseMapper.updateById(tSysDictData);
    }

    /**
     * 删除字典数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysDictDataByIds(String ids)
    {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
    }

    /**
     * 删除字典数据信息
     * 
     * @param id 字典数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysDictDataById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 删除字典数据信息
     *
     * @param queryWrapper
     * @return
     */
    @Override
    public int deleteTSysDictData(Wrapper<TSysDictData> queryWrapper) {
        return this.baseMapper.delete(queryWrapper);
    }

    /**
     * 检查name
     *
     * @param tSysDictData
     * @return
     */
    @Override
    public int checkNameUnique(TSysDictData tSysDictData) {
        QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
        queryWrapper.eq("dict_value", tSysDictData.getDictValue());
        return this.baseMapper.selectList(queryWrapper).size();
    }
}
