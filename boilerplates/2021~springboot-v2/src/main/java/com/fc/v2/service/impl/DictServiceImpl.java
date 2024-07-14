package com.fc.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.v2.model.auto.TSysDictData;
import com.fc.v2.model.auto.TSysDictType;
import com.fc.v2.service.ITDictService;
import com.fc.v2.service.ITSysDictDataService;
import com.fc.v2.service.ITSysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaozhi
 */
@Service("dict")
public class DictServiceImpl implements ITDictService {

    @Autowired
    private ITSysDictDataService itSysDictDataService;

    @Autowired
    private ITSysDictTypeService itSysDictTypeService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    @Override
    public List<TSysDictData> getType(String dictType) {
        QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
        if (dictType != null) {
            queryWrapper.eq("dict_type", dictType);
            return itSysDictDataService.selectTSysDictDataList(queryWrapper);
        }

        return new ArrayList<TSysDictData>();
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String getLabel(String dictType, String dictValue) {

        QueryWrapper<TSysDictData> queryWrapper = new QueryWrapper<TSysDictData>();
        if (dictType != null && dictValue != null) {
            queryWrapper.eq("dict_type", dictType).eq("dict_value", dictValue);
            List<TSysDictData> dictDatas = itSysDictDataService.selectTSysDictDataList(queryWrapper);
            if (dictDatas.size() > 0) {
                return dictDatas.get(0).getDictLabel();
            }
        }
        return "";
    }

    /**
     * 根字典类型查询字典
     *
     * @param dictType
     * @return
     */
    @Override
    public TSysDictType getSysDictType(String dictType) {
        QueryWrapper<TSysDictType> queryWrapper = new QueryWrapper<TSysDictType>();
        queryWrapper.eq("dict_type", dictType);
        List<TSysDictType> tSysDictTypes = itSysDictTypeService.selectTSysDictTypeList(queryWrapper);
        if (tSysDictTypes != null && tSysDictTypes.size() > 0) {
            return tSysDictTypes.get(0);
        }
        return null;

    }
}
