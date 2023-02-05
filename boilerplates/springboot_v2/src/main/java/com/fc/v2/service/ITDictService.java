package com.fc.v2.service;

import com.fc.v2.model.auto.TSysDictData;
import com.fc.v2.model.auto.TSysDictType;

import java.util.List;

/**
 * 字典Service接口
 *
 * @author zhaonz
 * @date 2021-08-05
 */
public interface ITDictService {

	/**
	 * 根据字典类型查询字典数据信息
	 *
	 * @param dictType
	 * @return
	 */
	public List<TSysDictData> getType(String dictType);

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 *
	 * @param dictType
	 * @param dictValue
	 * @return
	 */
	public String getLabel(String dictType, String dictValue);

	/**
	 * 根字典类型查询字典
	 *
	 * @param dictType
	 * @return
	 */
	public TSysDictType getSysDictType(String dictType);
}
