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

package com.taotao.cloud.workflow.biz.common.flowable.client.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 */
@Data
public class TaskVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8942395421885937298L;
    /** 任务id */
    private String taskId;

    /** 任务名称 */
    private String taskName;

    /** 任务key */
    private String taskDefinitionKey;

    /** 任务执行人名称 */
    private String assigneeName;

    /** 流程实例id */
    private String processInstanceId;

    /** 创建时间 */
    private Date createTime;

    /** 流程定义id */
    private String definitionId;

    /** 流程名称 */
    private String processName;

    /** 实例名称 */
    private String processInstanceName;

    /** 业务主键 */
    private String businessKey;
}
