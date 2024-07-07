/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
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

package com.taotao.cloud.monitor.model;

import java.util.List;
import java.util.Map;

/**
 * prometheus http sd 模型
 *
 * @author shuigedeng
 * @version 2021.9
 * @since 2021-09-02 20:01:42
 */
public class TargetGroup {

    private final List<String> targets;
    private final Map<String, String> labels;

    public TargetGroup(List<String> targets, Map<String, String> labels) {
        this.targets = targets;
        this.labels = labels;
    }

    public List<String> getTargets() {
        return targets;
    }

    public Map<String, String> getLabels() {
        return labels;
    }
}
