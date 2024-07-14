
package com.fc.v2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.v2.model.auto.TSysDictData;
import com.fc.v2.service.ITSysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fc.v2.mapper.auto.TSysDictTypeMapper;
import com.fc.v2.model.auto.TSysDictType;
import com.fc.v2.service.ITSysDictTypeService;

import cn.hutool.core.bean.BeanUtil;

import com.fc.v2.common.support.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 字典类型Service业务层处理
 * 
 * @author zhaonz
 * @date 2021-08-06
 */
@Service
public class TSysDictTypeServiceImpl  extends ServiceImpl<TSysDictTypeMapper, TSysDictType> implements ITSysDictTypeService
{
    private static final Logger logger = LoggerFactory.getLogger(TSysDictTypeServiceImpl.class);

    @Autowired
    private TSysDictTypeMapper tSysDictTypeMapper;

    @Autowired
    private ITSysDictDataService dataService;

    /**
     * 查询字典类型
     * 
     * @param id 字典类型ID
     * @return 字典类型
     */
    @Override
    public TSysDictType selectTSysDictTypeById(Long id)
    {
        return this.baseMapper.selectById(id);
    }

    /**
     * 查询字典类型列表
     * 
     * @param queryWrapper 字典类型
     * @return 字典类型
     */
    @Override
    public List<TSysDictType> selectTSysDictTypeList(Wrapper<TSysDictType> queryWrapper)
    {
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询字典类型列表
     *
     * @param tSysDictType 字典类型
     * @return 字典类型
     */
    @Override
    public List<TSysDictType> selectTSysDictTypeList(TSysDictType tSysDictType) {
    	Map<String, Object>  map = BeanUtil.beanToMap(tSysDictType, true, true);
    	QueryWrapper<TSysDictType> queryWrapper = new QueryWrapper<TSysDictType>();
    	queryWrapper.allEq(map,false);
    	return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * 新增字典类型
     * 
     * @param tSysDictType 字典类型
     * @return 结果
     */
    @Override
    public int insertTSysDictType(TSysDictType tSysDictType)
    {
        return this.baseMapper.insert(tSysDictType);
    }

    /**
     * 修改字典类型
     * 
     * @param tSysDictType 字典类型
     * @return 结果
     */
    @Override
    public int updateTSysDictType(TSysDictType tSysDictType)
    {
        return this.baseMapper.updateById(tSysDictType);
    }

    /**
     * 删除字典类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTSysDictTypeByIds(String ids)
    {
        //获取字典类型
        List<TSysDictType> tSysDictTypes = this.baseMapper.selectBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
        List<String> dictTypes = new ArrayList<String>();
        tSysDictTypes.forEach(tSysDictType -> dictTypes.add(tSysDictType.getDictType()));
        QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
        queryWrapper.in("dict_type", dictTypes);
        //删除字典类型
        if (dataService.deleteTSysDictData(queryWrapper) > 0){
            return this.baseMapper.deleteBatchIds(Arrays.asList(ConvertUtil.toStrArray(ids)));
        }
        return 0;
    }

    /**
     * 删除字典类型信息
     * 
     * @param id 字典类型ID
     * @return 结果
     */
    @Override
    public int deleteTSysDictTypeById(Long id)
    {
        return this.baseMapper.deleteById(id);
    }

    /**
     * 检查name
     *
     * @param tSysDictType
     * @return
     */
    @Override
    public int checkNameUnique(TSysDictType tSysDictType) {
        QueryWrapper<TSysDictType> queryWrapper = new QueryWrapper<TSysDictType>();
        queryWrapper.eq("dict_name", tSysDictType.getDictName());
        return this.baseMapper.selectList(queryWrapper).size();
    }
}
