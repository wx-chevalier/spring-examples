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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.laokou.common.i18n.core.HttpResult;
import org.laokou.flowable.client.dto.DefinitionDTO;
import org.laokou.flowable.client.vo.DefinitionVO;
import org.laokou.flowable.client.vo.PageVO;
import org.laokou.flowable.server.service.WorkDefinitionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@RestController
@RequestMapping("/work/definition/api")
@RequiredArgsConstructor
@Tag(name = "Work Definition API", description = "流程定义API")
public class WorkDefinitionApiController {
    private final WorkDefinitionService workDefinitionService;

    @PostMapping(value = "/insert")
    @Operation(summary = "流程定义>新增流程", description = "流程定义>新增流程")
    public HttpResult<Boolean> insert(@Valid @NotNull(message = "请上传相关流程图") @RequestPart("file") MultipartFile file)
            throws IOException {
        return new HttpResult<Boolean>().ok(workDefinitionService.insertDefinition(file.getInputStream()));
    }

    @PostMapping(value = "/query")
    @Operation(summary = "流程定义>查询流程", description = "流程定义>查询流程")
    public HttpResult<PageVO<DefinitionVO>> query(@RequestBody DefinitionDTO dto) {
        return new HttpResult<PageVO<DefinitionVO>>().ok(workDefinitionService.queryDefinitionPage(dto));
    }

    @GetMapping(value = "/diagram")
    @Operation(summary = "流程定义>流程图", description = "流程定义>流程图")
    public HttpResult<String> diagram(@RequestParam("definitionId") String definitionId) {
        return new HttpResult<String>().ok(workDefinitionService.diagramDefinition(definitionId));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "流程定义>删除流程", description = "流程定义>删除流程")
    public HttpResult<Boolean> delete(@RequestParam("deploymentId") String deploymentId) {
        return new HttpResult<Boolean>().ok(workDefinitionService.deleteDefinition(deploymentId));
    }

    @PutMapping(value = "/suspend")
    @Operation(summary = "流程定义>挂起流程", description = "流程定义>挂起流程")
    public HttpResult<Boolean> suspend(@RequestParam("definitionId") String definitionId) {
        return new HttpResult<Boolean>().ok(workDefinitionService.suspendDefinition(definitionId));
    }

    @PutMapping(value = "/activate")
    @Operation(summary = "流程定义>激活流程", description = "流程定义>激活流程")
    public HttpResult<Boolean> activate(@RequestParam("definitionId") String definitionId) {
        return new HttpResult<Boolean>().ok(workDefinitionService.activateDefinition(definitionId));
    }
}
