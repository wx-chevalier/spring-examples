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

package com.taotao.cloud.workflow.biz.flowable.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.laokou.common.i18n.core.HttpResult;
import org.laokou.flowable.client.dto.*;
import org.laokou.flowable.client.vo.AssigneeVO;
import org.laokou.flowable.client.vo.PageVO;
import org.laokou.flowable.client.vo.TaskVO;
import org.laokou.flowable.server.service.WorkTaskService;
import org.springframework.web.bind.annotation.*;

/**
 * 
 */
@RestController
@Tag(name = "Work Task API", description = "流程任务API")
@RequestMapping("/work/task/api")
@RequiredArgsConstructor
public class WorkTaskApiController {

    private final WorkTaskService workTaskService;

    @PostMapping(value = "/query")
    @Operation(summary = "流程任务>任务查询", description = "流程任务>任务查询")
    public HttpResult<PageVO<TaskVO>> query(@RequestBody TaskDTO dto) {
        return new HttpResult<PageVO<TaskVO>>().ok(workTaskService.queryTaskPage(dto));
    }

    @PostMapping(value = "/audit")
    @Operation(summary = "流程任务>任务审批", description = "流程任务>任务审批")
    public HttpResult<AssigneeVO> audit(@RequestBody AuditDTO dto) {
        return new HttpResult<AssigneeVO>().ok(workTaskService.auditTask(dto));
    }

    @PostMapping(value = "/resolve")
    @Operation(summary = "流程任务>任务处理", description = "流程任务>任务处理")
    public HttpResult<AssigneeVO> resolve(@RequestBody ResolveDTO dto) {
        return new HttpResult<AssigneeVO>().ok(workTaskService.resolveTask(dto));
    }

    @PostMapping(value = "/start")
    @Operation(summary = "流程任务>任务开始", description = "流程任务>任务开始")
    public HttpResult<AssigneeVO> start(@RequestBody ProcessDTO dto) {
        return new HttpResult<AssigneeVO>().ok(workTaskService.startTask(dto));
    }

    @GetMapping(value = "/diagram")
    @Operation(summary = "流程任务>任务流程", description = "流程任务>任务流程")
    public HttpResult<String> diagram(@RequestParam("processInstanceId") String processInstanceId) throws IOException {
        return new HttpResult<String>().ok(workTaskService.diagramTask(processInstanceId));
    }

    @PostMapping("/transfer")
    @Operation(summary = "流程任务>任务转办", description = "流程任务>任务转办")
    public HttpResult<AssigneeVO> transfer(@RequestBody TransferDTO dto) {
        return new HttpResult<AssigneeVO>().ok(workTaskService.transferTask(dto));
    }

    @PostMapping("/delegate")
    @Operation(summary = "流程任务>任务委派", description = "流程任务>任务委派")
    public HttpResult<AssigneeVO> delegate(@RequestBody DelegateDTO dto) {
        return new HttpResult<AssigneeVO>().ok(workTaskService.delegateTask(dto));
    }
}
