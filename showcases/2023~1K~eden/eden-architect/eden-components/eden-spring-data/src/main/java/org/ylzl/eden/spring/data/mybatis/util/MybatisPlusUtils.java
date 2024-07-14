/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.spring.data.mybatis.util;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.ylzl.eden.commons.lang.Strings;
import org.ylzl.eden.commons.safe.SqlSafeUtils;

import java.util.Arrays;
import java.util.List;

/**
 * MybatisPlus 工具集
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@UtilityClass
public class MybatisPlusUtils {

	/**
	 * 追加 Like %
	 *
	 * @param column 字段
	 * @return
	 */
	public static String appendLikeSuffix(String column) {
		return StringUtils.join(column, "%");
	}

	/**
	 * 追加 23:59:59
	 *
	 * @param column 字段
	 * @return
	 */
	public static String appendEndTimeSuffix(String column) {
		return StringUtils.join(column, " 23:59:59");
	}

	/**
	 * 追加 00:00:00
	 *
	 * @param column 字段
	 * @return
	 */
	public static String appendBeginTimeSuffix(String column) {
		return StringUtils.join(column, " 00:00:00");
	}

	/**
	 * 构建 Page 对象
	 *
	 * @param pageNum    页号
	 * @param pageSize   行数
	 * @param sortColumn 排序字段
	 * @param sortRule   排序规则
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Page<T> buildPage(
		long pageNum, long pageSize, String sortColumn, String sortRule) {
		Page<T> page = new Page(pageNum, pageSize);
		if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortRule)) {
			String[] sortColumns = sortColumn.split(Strings.COLON);
			List<OrderItem> orderItems = SortRule.ASC.name().equalsIgnoreCase(sortRule)
				? OrderItem.ascs(sortColumns)
				: OrderItem.descs(sortColumns);
			page.addOrder(orderItems);
		}
		return page;
	}

	/**
	 * 获取表名
	 *
	 * @param clazz domain
	 * @return
	 */
	public static String getTableName(Class<?> clazz) {
		TableName tableName = clazz.getAnnotation(TableName.class);
		return tableName.value();
	}

	/**
	 * 获取排序 SQL
	 *
	 * @param sortColumn 排序字段
	 * @param sortRule   排序规则（asc/desc）
	 * @return
	 */
	public static String getOrderBySQL(String sortColumn, String sortRule) {
		checkSQLInjection(sortColumn);
		checkSQLInjection(sortRule);
		StringBuilder sql = new StringBuilder();
		if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortRule)) {
			sql.append(" ORDER BY ");
			String rule =
				Arrays.stream(SortRule.values())
					.filter(sortRuleEnum -> sortRuleEnum.name().equalsIgnoreCase(sortRule))
					.findFirst()
					.get()
					.name();
			String[] sortColumns = sortColumn.split(Strings.COLON);
			int len = sortColumns.length;
			int i = 0;
			for (String column : sortColumns) {
				sql.append(column).append(Strings.SPACE).append(rule);
				i++;
				if (i < len) {
					sql.append(Strings.COMMA);
				}
			}
		}
		return sql.toString();
	}

	/**
	 * 获取分页 SQL
	 *
	 * @param pageNum  页号
	 * @param pageSize 行数
	 * @return
	 */
	public static String getLimitSQL(long pageNum, long pageSize) {
		return " LIMIT " + ((pageNum - 1) * pageSize) + ", " + pageSize;
	}

	/**
	 * 检查是否有 SQL 注入
	 *
	 * @param str 输入
	 */
	private void checkSQLInjection(String str) {
		if (!SqlSafeUtils.isSQLInjectionSafe(str)) {
			throw new RuntimeException("Found sql injection: " + str);
		}
	}

	/**
	 * 受影响行数转换成布尔值
	 *
	 * @param effectiveRows
	 * @return
	 */
	public static boolean effectiveRowsToBoolean(int effectiveRows) {
		return effectiveRows > 0;
	}
}
