package com.fc.v2.service;

import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.custom.TsysTables;
import com.fc.v2.model.custom.autocode.BeanColumn;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 生成代码Service接口
 *
 * @author
 * @date 2021-08-05
 */
public interface ITGeneratorService {

	/**
	 * 分页查询
	 *
	 * @param tablepar
	 * @param searchText
	 * @return
	 */
	public PageInfo<TsysTables> list(Tablepar tablepar, String searchText);

	/**
	 * 查询具体某表信息
	 *
	 * @param tableName
	 * @return
	 */
	public List<TsysTables> queryList(String tableName);

	/**
	 * 查询表详情
	 *
	 * @param tableName
	 * @return
	 */
	public List<BeanColumn> queryColumns2(String tableName);
}
