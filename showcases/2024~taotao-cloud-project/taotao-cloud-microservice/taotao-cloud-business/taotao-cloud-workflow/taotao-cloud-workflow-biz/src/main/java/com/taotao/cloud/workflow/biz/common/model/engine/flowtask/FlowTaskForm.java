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

package com.taotao.cloud.workflow.biz.common.model.engine.flowtask;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class FlowTaskForm {
    /** 引擎id* */
    private String flowId;
    /** 界面数据* */
    private String data;
    /** 0.提交 1.保存* */
    private String status;
    /** 指定用户* */
    private String freeApproverUserId;
    /** 候选人 */
    private Map<String, List<String>> candidateList;
}