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
package io.github.smart.cloud.starter.web.exception.strategy;

import io.github.smart.cloud.constants.CommonReturnCodes;
import io.github.smart.cloud.common.pojo.ResponseHead;
import io.github.smart.cloud.starter.core.business.util.RespHeadUtil;
import io.github.smart.cloud.starter.web.exception.IExceptionHandlerStrategy;

/**
 * @author collin
 * @desc 参数不合法异常转换
 * @date 2019/10/29
 */
public class IllegalArgumentExceptionHandlerStrategy implements IExceptionHandlerStrategy {

    @Override
    public boolean match(Throwable e) {
        return e instanceof IllegalArgumentException;
    }

    @Override
    public ResponseHead transRespHead(Throwable e) {
        return RespHeadUtil.ofI18n(CommonReturnCodes.VALIDATE_FAIL);
    }

}