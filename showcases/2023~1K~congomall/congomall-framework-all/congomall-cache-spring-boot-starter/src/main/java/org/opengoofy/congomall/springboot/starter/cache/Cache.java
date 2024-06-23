/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opengoofy.congomall.springboot.starter.cache;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 缓存接口
 *
 * @author chen.ma
 * @github <a href="https://github.com/opengoofy" />
 * @公众号 马丁玩编程，关注回复：资料，领取后端技术专家成长手册
 */
public interface Cache {
    
    /**
     * 获取缓存
     */
    <T> T get(@NotBlank String key, Class<T> clazz);
    
    /**
     * 放入缓存
     */
    void put(@NotBlank String key, Object value);
    
    /**
     * 如果 keys 全部不存在，则新增，返回 true，反之 false
     */
    Boolean putIfAllAbsent(@NotNull Collection<String> keys);
    
    /**
     * 删除缓存
     */
    Boolean delete(@NotBlank String key);
    
    /**
     * 删除 keys，返回删除数量
     */
    Long delete(@NotNull Collection<String> keys);
    
    /**
     * 判断 key 是否存在
     */
    Boolean hasKey(@NotBlank String key);
    
    /**
     * 获取缓存组件实例
     */
    Object getInstance();
}
