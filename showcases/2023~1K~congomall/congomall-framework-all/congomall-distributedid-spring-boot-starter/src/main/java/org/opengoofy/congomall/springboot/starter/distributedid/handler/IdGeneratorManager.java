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

package org.opengoofy.congomall.springboot.starter.distributedid.handler;

import lombok.NonNull;
import org.opengoofy.congomall.springboot.starter.distributedid.core.IdGenerator;
import org.opengoofy.congomall.springboot.starter.distributedid.core.serviceid.DefaultServiceIdGenerator;
import org.opengoofy.congomall.springboot.starter.distributedid.core.serviceid.ServiceIdGenerator;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ID 生成器管理
 *
 * @author chen.ma
 * @github <a href="https://github.com/opengoofy" />
 * @公众号 马丁玩编程，关注回复：资料，领取后端技术专家成长手册
 */
public final class IdGeneratorManager {
    
    /**
     * ID 生成器管理容器
     */
    private static Map<String, IdGenerator> MANAGER = new ConcurrentHashMap<>();
    
    /**
     * 注册默认 ID 生成器
     */
    static {
        MANAGER.put("default", new DefaultServiceIdGenerator());
    }
    
    /**
     * 注册 ID 生成器
     */
    public static void registerIdGenerator(@NonNull String resource, @NonNull IdGenerator idGenerator) {
        IdGenerator actual = MANAGER.get(resource);
        if (actual != null) {
            return;
        }
        MANAGER.put(resource, idGenerator);
    }
    
    /**
     * 根据 {@param resource} 获取 ID 生成器
     */
    public static IdGenerator getIdGenerator(@NonNull String resource) {
        return MANAGER.get(resource);
    }
    
    /**
     * 获取默认 ID 生成器 {@link DefaultServiceIdGenerator}
     */
    public static ServiceIdGenerator getDefaultServiceIdGenerator() {
        return Optional.ofNullable(MANAGER.get("default")).map(each -> (ServiceIdGenerator) each).orElse(null);
    }
}
