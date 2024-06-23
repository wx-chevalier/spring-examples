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
package io.github.smart.cloud.starter.web.exception;

import io.github.smart.cloud.common.pojo.ResponseHead;

/**
 * 接口异常转换
 *
 * @author collin
 * @date 2019/10/29
 */
public interface IExceptionHandlerStrategy {

    /**
     * 是否需要servlet环境
     *
     * @return
     */
    default boolean isNeedServletEnv() {
        return false;
    }

    /**
     * 异常类型匹配
     *
     * @param e
     * @return
     */
    boolean match(Throwable e);

    /**
     * 将异常转化为响应体
     *
     * @param e
     * @return
     */
    ResponseHead transRespHead(Throwable e);

}