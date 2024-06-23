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
package io.github.smart.cloud.mask;

/**
 * @author collin
 * @desc mask配置
 * @date 2019/11/05
 */
public class DefaultMaskConfig {

    /**
     * 默认开头保留的长度
     */
    public static final int START_LEN = 0;

    /**
     * 默认结尾保留的长度
     */
    public static final int END_LEN = 0;

    /**
     * 默认的掩码
     */
    public static final String MASK_TEXT = "***";

    private DefaultMaskConfig() {
    }

}