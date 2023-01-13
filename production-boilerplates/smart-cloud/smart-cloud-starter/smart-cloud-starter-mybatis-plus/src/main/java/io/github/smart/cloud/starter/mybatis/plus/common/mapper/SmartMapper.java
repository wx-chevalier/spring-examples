/*
 * Copyright © 2019 collin (1634753825@qq.com)
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
package io.github.smart.cloud.starter.mybatis.plus.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * smart cloud BaseMapper
 *
 * @author collin
 * @date 2021-03-23
 */
public interface SmartMapper<T> extends BaseMapper<T> {

    /**
     * in-line式批量插入
     *
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(@Param("list") List<T> entityList);

    /**
     * 清空表数据
     *
     * @return
     */
    void truncate();

}