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

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.number.NumberUtils;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.task.vo.instance.BpmProcessInstancePageItemRespVO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.task.vo.instance.BpmProcessInstanceRespVO;
import com.taotao.cloud.flowable.biz.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import com.taotao.cloud.flowable.biz.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import com.taotao.cloud.flowable.biz.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import com.taotao.cloud.flowable.biz.bpm.service.message.dto.BpmMessageSendWhenProcessInstanceApproveReqDTO;
import com.taotao.cloud.flowable.biz.bpm.service.message.dto.BpmMessageSendWhenProcessInstanceRejectReqDTO;
import java.util.List;
import java.util.Map;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 流程实例 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface BpmProcessInstanceConvert {

    BpmProcessInstanceConvert INSTANCE = Mappers.getMapper(BpmProcessInstanceConvert.class);

    default PageResult<BpmProcessInstancePageItemRespVO> convertPage(
            PageResult<BpmProcessInstanceExtDO> page, Map<String, List<Task>> taskMap) {
        List<BpmProcessInstancePageItemRespVO> list = convertList(page.getList());
        list.forEach(respVO -> respVO.setTasks(convertList2(taskMap.get(respVO.getId()))));
        return new PageResult<>(list, page.getTotal());
    }

    List<BpmProcessInstancePageItemRespVO> convertList(List<BpmProcessInstanceExtDO> list);

    @Mapping(source = "processInstanceId", target = "id")
    BpmProcessInstancePageItemRespVO convert(BpmProcessInstanceExtDO bean);

    List<BpmProcessInstancePageItemRespVO.Task> convertList2(List<Task> tasks);

    default BpmProcessInstanceRespVO convert2(
            HistoricProcessInstance processInstance,
            BpmProcessInstanceExtDO processInstanceExt,
            ProcessDefinition processDefinition,
            BpmProcessDefinitionExtDO processDefinitionExt,
            String bpmnXml,
            AdminUserRespDTO startUser,
            DeptRespDTO dept) {
        BpmProcessInstanceRespVO respVO = convert2(processInstance);
        copyTo(processInstanceExt, respVO);
        // definition
        respVO.setProcessDefinition(convert2(processDefinition));
        copyTo(processDefinitionExt, respVO.getProcessDefinition());
        respVO.getProcessDefinition().setBpmnXml(bpmnXml);
        // user
        if (startUser != null) {
            respVO.setStartUser(convert2(startUser));
            if (dept != null) {
                respVO.getStartUser().setDeptName(dept.getName());
            }
        }
        return respVO;
    }

    BpmProcessInstanceRespVO convert2(HistoricProcessInstance bean);

    @Mapping(source = "from.id", target = "to.id", ignore = true)
    void copyTo(BpmProcessInstanceExtDO from, @MappingTarget BpmProcessInstanceRespVO to);

    BpmProcessInstanceRespVO.ProcessDefinition convert2(ProcessDefinition bean);

    @Mapping(source = "from.id", target = "to.id", ignore = true)
    void copyTo(BpmProcessDefinitionExtDO from, @MappingTarget BpmProcessInstanceRespVO.ProcessDefinition to);

    BpmProcessInstanceRespVO.User convert2(AdminUserRespDTO bean);

    default BpmProcessInstanceResultEvent convert(Object source, HistoricProcessInstance instance, Integer result) {
        BpmProcessInstanceResultEvent event = new BpmProcessInstanceResultEvent(source);
        event.setId(instance.getId());
        event.setProcessDefinitionKey(instance.getProcessDefinitionKey());
        event.setBusinessKey(instance.getBusinessKey());
        event.setResult(result);
        return event;
    }

    default BpmProcessInstanceResultEvent convert(Object source, ProcessInstance instance, Integer result) {
        BpmProcessInstanceResultEvent event = new BpmProcessInstanceResultEvent(source);
        event.setId(instance.getId());
        event.setProcessDefinitionKey(instance.getProcessDefinitionKey());
        event.setBusinessKey(instance.getBusinessKey());
        event.setResult(result);
        return event;
    }

    default BpmMessageSendWhenProcessInstanceApproveReqDTO convert2ApprovedReq(ProcessInstance instance) {
        return new BpmMessageSendWhenProcessInstanceApproveReqDTO()
                .setStartUserId(NumberUtils.parseLong(instance.getStartUserId()))
                .setProcessInstanceId(instance.getId())
                .setProcessInstanceName(instance.getName());
    }

    default BpmMessageSendWhenProcessInstanceRejectReqDTO convert2RejectReq(ProcessInstance instance, String reason) {
        return new BpmMessageSendWhenProcessInstanceRejectReqDTO()
                .setProcessInstanceName(instance.getName())
                .setProcessInstanceId(instance.getId())
                .setReason(reason)
                .setStartUserId(NumberUtils.parseLong(instance.getStartUserId()));
    }
}
