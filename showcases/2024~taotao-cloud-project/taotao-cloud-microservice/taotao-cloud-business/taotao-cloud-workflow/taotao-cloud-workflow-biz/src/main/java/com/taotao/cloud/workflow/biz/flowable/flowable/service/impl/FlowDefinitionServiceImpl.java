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

package com.taotao.cloud.workflow.biz.flowable.flowable.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.factory.FlowServiceFactory;
import com.ruoyi.flowable.service.IFlowDefinitionService;
import com.ruoyi.flowable.service.ISysDeployFormService;
import com.ruoyi.system.domain.FlowProcDefDto;
import com.ruoyi.system.domain.SysForm;
import com.ruoyi.system.mapper.FlowDeployMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.stereotype.Service;

/**
 * 流程定义
 *
 * @author Tony
 * @since 2021-04-03
 */
@Service
@Slf4j
public class FlowDefinitionServiceImpl extends FlowServiceFactory implements IFlowDefinitionService {

    @Resource
    private ISysDeployFormService sysDeployFormService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysDeptService sysDeptService;

    @Resource
    private ISysPostService postService;

    @Resource
    private FlowDeployMapper flowDeployMapper;

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery =
                repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);
        long count = processDefinitionQuery.count();
        return count > 0 ? true : false;
    }

    /**
     * 流程定义列表
     *
     * @param pageNum 当前页码
     * @param pageSize 每页条数
     * @return 流程定义分页列表数据
     */
    @Override
    public Page<FlowProcDefDto> list(String name, Integer pageNum, Integer pageSize) {
        Page<FlowProcDefDto> page = new Page<>();
        //        // 流程定义列表数据查询
        //        final ProcessDefinitionQuery processDefinitionQuery =
        // repositoryService.createProcessDefinitionQuery();
        //        if (StringUtils.isNotEmpty(name)) {
        //            processDefinitionQuery.processDefinitionNameLike(name);
        //        }
        ////        processDefinitionQuery.orderByProcessDefinitionKey().asc();
        //        page.setTotal(processDefinitionQuery.count());
        //        List<ProcessDefinition> processDefinitionList =
        // processDefinitionQuery.listPage(pageSize * (pageNum - 1), pageSize);
        //
        //        List<FlowProcDefDto> dataList = new ArrayList<>();
        //        for (ProcessDefinition processDefinition : processDefinitionList) {
        //            String deploymentId = processDefinition.getDeploymentId();
        //            Deployment deployment =
        // repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        //            FlowProcDefDto reProcDef = new FlowProcDefDto();
        //            BeanUtils.copyProperties(processDefinition, reProcDef);
        //            SysForm sysForm =
        // sysDeployFormService.selectSysDeployFormByDeployId(deploymentId);
        //            if (Objects.nonNull(sysForm)) {
        //                reProcDef.setFormName(sysForm.getFormName());
        //                reProcDef.setFormId(sysForm.getFormId());
        //            }
        //            // 流程定义时间
        //            reProcDef.setDeploymentTime(deployment.getDeploymentTime());
        //            dataList.add(reProcDef);
        //        }
        PageHelper.startPage(pageNum, pageSize);
        final List<FlowProcDefDto> dataList = flowDeployMapper.selectDeployList(name);
        // 加载挂表单
        for (FlowProcDefDto procDef : dataList) {
            SysForm sysForm = sysDeployFormService.selectSysDeployFormByDeployId(procDef.getDeploymentId());
            if (Objects.nonNull(sysForm)) {
                procDef.setFormName(sysForm.getFormName());
                procDef.setFormId(sysForm.getFormId());
            }
        }
        page.setTotal(new PageInfo(dataList).getTotal());
        page.setRecords(dataList);
        return page;
    }

    /**
     * 导入流程文件
     *
     * <p>当每个key的流程第一次部署时，指定版本为1。对其后所有使用相同key的流程定义， 部署时版本会在该key当前已部署的最高版本号基础上加1。key参数用于区分流程定义
     *
     * @param name
     * @param category
     * @param in
     */
    @Override
    public void importFile(String name, String category, InputStream in) {
        Deployment deploy = repositoryService
                .createDeployment()
                .addInputStream(name + BPMN_FILE_SUFFIX, in)
                .name(name)
                .category(category)
                .deploy();
        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), category);
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    @Override
    public AjaxResult readXml(String deployId) throws IOException {
        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        InputStream inputStream =
                repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return AjaxResult.success("", result);
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    @Override
    public InputStream readImage(String deployId) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        // 获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        // 输出为图片
        return diagramGenerator.generateDiagram(
                bpmnModel, "png", Collections.emptyList(), Collections.emptyList(), "宋体", "宋体", "宋体", null, 1.0, false);
    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId 流程模板ID
     * @param variables 流程变量
     * @return
     */
    @Override
    public AjaxResult startProcessInstanceById(String procDefId, Map<String, Object> variables) {
        try {
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionId(procDefId)
                    .latestVersion()
                    .singleResult();
            if (Objects.nonNull(processDefinition) && processDefinition.isSuspended()) {
                return AjaxResult.error("流程已被挂起,请先激活流程");
            }
            // 设置流程发起人Id到流程中
            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
            identityService.setAuthenticatedUserId(sysUser.getUserId().toString());
            variables.put(ProcessConstants.PROCESS_INITIATOR, sysUser.getUserId());
            runtimeService.startProcessInstanceById(procDefId, variables);
            // 流程发起时 跳过发起人节点
            //            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
            //            identityService.setAuthenticatedUserId(sysUser.getUserId().toString());
            //            variables.put(ProcessConstants.PROCESS_INITIATOR, "");
            //            ProcessInstance processInstance =
            // runtimeService.startProcessInstanceById(procDefId, variables);
            //            // 给第一步申请人节点设置任务执行人和意见
            //            Task task =
            // taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            //            if (Objects.nonNull(task)) {
            //                taskService.addComment(task.getId(),
            // processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(),
            // sysUser.getNickName() + "发起流程申请");
            ////                taskService.setAssignee(task.getId(),
            // sysUser.getUserId().toString());
            //                taskService.complete(task.getId(), variables);
            //            }
            return AjaxResult.success("流程启动成功");
        } catch (Exception e) {
            LogUtils.error(e);
            return AjaxResult.error("流程启动错误");
        }
    }

    /**
     * 激活或挂起流程定义
     *
     * @param state 状态
     * @param deployId 流程部署ID
     */
    @Override
    public void updateState(Integer state, String deployId) {
        ProcessDefinition procDef = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        // 激活
        if (state == 1) {
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, null);
        }
        // 挂起
        if (state == 2) {
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, null);
        }
    }

    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    @Override
    public void delete(String deployId) {
        // true 允许级联删除 ,不设置会导致数据库外键关联异常
        repositoryService.deleteDeployment(deployId, true);
    }
}
