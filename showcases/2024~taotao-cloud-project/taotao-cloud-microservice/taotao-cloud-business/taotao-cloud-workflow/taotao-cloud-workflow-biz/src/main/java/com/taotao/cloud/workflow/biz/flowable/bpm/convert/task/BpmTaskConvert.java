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

package com.taotao.cloud.workflow.biz.flowable.bpm.convert.task;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.number.NumberUtils;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.task.vo.task.BpmTaskDonePageItemRespVO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.task.vo.task.BpmTaskRespVO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.task.vo.task.BpmTaskTodoPageItemRespVO;
import com.taotao.cloud.flowable.biz.bpm.dal.dataobject.task.BpmTaskExtDO;
import com.taotao.cloud.flowable.biz.bpm.service.message.dto.BpmMessageSendWhenTaskCreatedReqDTO;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

/**
 * Bpm 任务 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface BpmTaskConvert {

    BpmTaskConvert INSTANCE = Mappers.getMapper(BpmTaskConvert.class);

    /**
     * 复制对象
     *
     * @param source 源 要复制的对象
     * @param target 目标 复制到此对象
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        try {
            T newInstance = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default <T, K> List<K> copyList(List<T> source, Class<K> target) {
        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(e -> copy(e, target)).toList();
    }

    default List<BpmTaskTodoPageItemRespVO> convertList1(
            List<Task> tasks, Map<String, ProcessInstance> processInstanceMap, Map<Long, AdminUserRespDTO> userMap) {
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskTodoPageItemRespVO respVO = convert1(task);
            ProcessInstance processInstance = processInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                AdminUserRespDTO startUser = userMap.get(NumberUtils.parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            return respVO;
        });
    }

    @Mapping(source = "suspended", target = "suspensionState", qualifiedByName = "convertSuspendedToSuspensionState")
    @Mapping(
            target = "claimTime",
            expression = "java(bean.getClaimTime()==null?null:"
                    + " LocalDateTime.ofInstant(bean.getClaimTime().toInstant(),ZoneId.systemDefault()))")
    @Mapping(
            target = "createTime",
            expression =
                    "java(bean.getCreateTime()==null?null:LocalDateTime.ofInstant(bean.getCreateTime().toInstant(),ZoneId.systemDefault()))")
    BpmTaskTodoPageItemRespVO convert1(Task bean);

    @Named("convertSuspendedToSuspensionState")
    default Integer convertSuspendedToSuspensionState(boolean suspended) {
        return suspended ? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode();
    }

    default List<BpmTaskDonePageItemRespVO> convertList2(
            List<HistoricTaskInstance> tasks,
            Map<String, BpmTaskExtDO> bpmTaskExtDOMap,
            Map<String, HistoricProcessInstance> historicProcessInstanceMap,
            Map<Long, AdminUserRespDTO> userMap) {
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskDonePageItemRespVO respVO = convert2(task);
            BpmTaskExtDO taskExtDO = bpmTaskExtDOMap.get(task.getId());
            copyTo(taskExtDO, respVO);
            HistoricProcessInstance processInstance = historicProcessInstanceMap.get(task.getProcessInstanceId());
            if (processInstance != null) {
                AdminUserRespDTO startUser = userMap.get(NumberUtils.parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            return respVO;
        });
    }

    BpmTaskDonePageItemRespVO convert2(HistoricTaskInstance bean);

    @Mappings({
        @Mapping(source = "processInstance.id", target = "id"),
        @Mapping(source = "processInstance.name", target = "name"),
        @Mapping(source = "processInstance.startUserId", target = "startUserId"),
        @Mapping(source = "processInstance.processDefinitionId", target = "processDefinitionId"),
        @Mapping(source = "startUser.nickname", target = "startUserNickname")
    })
    BpmTaskTodoPageItemRespVO.ProcessInstance convert(ProcessInstance processInstance, AdminUserRespDTO startUser);

    default List<BpmTaskRespVO> convertList3(
            List<HistoricTaskInstance> tasks,
            Map<String, BpmTaskExtDO> bpmTaskExtDOMap,
            HistoricProcessInstance processInstance,
            Map<Long, AdminUserRespDTO> userMap,
            Map<Long, DeptRespDTO> deptMap) {
        return CollectionUtils.convertList(tasks, task -> {
            BpmTaskRespVO respVO = convert3(task);
            BpmTaskExtDO taskExtDO = bpmTaskExtDOMap.get(task.getId());
            copyTo(taskExtDO, respVO);
            if (processInstance != null) {
                AdminUserRespDTO startUser = userMap.get(NumberUtils.parseLong(processInstance.getStartUserId()));
                respVO.setProcessInstance(convert(processInstance, startUser));
            }
            AdminUserRespDTO assignUser = userMap.get(NumberUtils.parseLong(task.getAssignee()));
            if (assignUser != null) {
                respVO.setAssigneeUser(convert3(assignUser));
                DeptRespDTO dept = deptMap.get(assignUser.getDeptId());
                if (dept != null) {
                    respVO.getAssigneeUser().setDeptName(dept.getName());
                }
            }
            return respVO;
        });
    }

    @Mapping(source = "taskDefinitionKey", target = "definitionKey")
    BpmTaskRespVO convert3(HistoricTaskInstance bean);

    BpmTaskRespVO.User convert3(AdminUserRespDTO bean);

    @Mapping(target = "id", ignore = true)
    void copyTo(BpmTaskExtDO from, @MappingTarget BpmTaskDonePageItemRespVO to);

    @Mappings({
        @Mapping(source = "processInstance.id", target = "id"),
        @Mapping(source = "processInstance.name", target = "name"),
        @Mapping(source = "processInstance.startUserId", target = "startUserId"),
        @Mapping(source = "processInstance.processDefinitionId", target = "processDefinitionId"),
        @Mapping(source = "startUser.nickname", target = "startUserNickname")
    })
    BpmTaskTodoPageItemRespVO.ProcessInstance convert(
            HistoricProcessInstance processInstance, AdminUserRespDTO startUser);

    default BpmTaskExtDO convert2TaskExt(Task task) {
        BpmTaskExtDO taskExtDO = new BpmTaskExtDO()
                .setTaskId(task.getId())
                .setAssigneeUserId(NumberUtils.parseLong(task.getAssignee()))
                .setName(task.getName())
                .setProcessDefinitionId(task.getProcessDefinitionId())
                .setProcessInstanceId(task.getProcessInstanceId());
        taskExtDO.setCreateTime(LocalDateTimeUtil.of(task.getCreateTime()));
        return taskExtDO;
    }

    default BpmMessageSendWhenTaskCreatedReqDTO convert(
            ProcessInstance processInstance, AdminUserRespDTO startUser, Task task) {
        BpmMessageSendWhenTaskCreatedReqDTO reqDTO = new BpmMessageSendWhenTaskCreatedReqDTO();
        reqDTO.setProcessInstanceId(processInstance.getProcessInstanceId())
                .setProcessInstanceName(processInstance.getName())
                .setStartUserId(startUser.getId())
                .setStartUserNickname(startUser.getNickname())
                .setTaskId(task.getId())
                .setTaskName(task.getName())
                .setAssigneeUserId(NumberUtils.parseLong(task.getAssignee()));
        return reqDTO;
    }
}
