/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.codegen.entity;

import lombok.Data;

/**
 * @author lengleng
 * @date 2018/07/29 列属性： https://blog.csdn.net/lkforce/article/details/79557482
 */
@Data
public class ColumnEntity {

	/**
	 * 列表
	 */
	private String columnName;

	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 驼峰属性
	 */
	private String caseAttrName;

	/**
	 * 普通属性
	 */
	private String lowerAttrName;

	/**
	 * 属性类型
	 */
	private String attrType;

	/**
	 * 其他信息
	 */
	private String extra;

	/**
	 * 字段类型
	 */
	private String columnType;

	/**
	 * 是否可以为空
	 */
	private Boolean nullable;

	/**
	 * 是否隐藏
	 */
	private Boolean hidden;

}
