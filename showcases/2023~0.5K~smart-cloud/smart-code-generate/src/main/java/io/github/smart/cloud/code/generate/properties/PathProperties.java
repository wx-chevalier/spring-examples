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
package io.github.smart.cloud.code.generate.properties;

import lombok.Setter;
import lombok.ToString;
import io.github.smart.cloud.code.generate.util.PathUtil;

/**
 * @author collin
 * @desc 工程路径配置
 * @date 2019/11/08
 */
@Setter
@ToString
public class PathProperties {

    /**
     * rpc工程路径
     */
    private String rpc;
    /**
     * 服务工程路径
     */
    private String service;

    public String getRpc() {
        if (rpc != null && rpc.trim().length() > 0) {
            return rpc;
        }
        return PathUtil.getDefaultRpcDir();
    }

    public String getService() {
        if (service != null && service.trim().length() > 0) {
            return service;
        }
        return PathUtil.getDefaultServiceDir();
    }

}