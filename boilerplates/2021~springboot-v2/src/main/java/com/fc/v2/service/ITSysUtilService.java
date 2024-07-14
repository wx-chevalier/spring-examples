package com.fc.v2.service;

import java.util.List;
import java.util.Map;

/**
 * @author fuce
 */
public interface ITSysUtilService {

	/**
	 * 执行sql
	 *
	 * @param sql
	 * @return
	 */
	abstract public int executeSQL(String sql);

	/**
	 * 查询sql
	 *
	 * @param sql
	 * @return
	 */
	abstract public List<Map<Object, Object>> SelectExecuteSQL(String sql);
}
