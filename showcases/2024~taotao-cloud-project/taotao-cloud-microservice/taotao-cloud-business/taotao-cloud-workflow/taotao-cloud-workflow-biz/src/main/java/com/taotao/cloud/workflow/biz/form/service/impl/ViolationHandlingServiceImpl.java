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

package com.taotao.cloud.workflow.biz.form.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taotao.cloud.common.utils.common.JsonUtils;
import com.taotao.cloud.workflow.biz.common.model.form.violationhandling.ViolationHandlingForm;
import com.taotao.cloud.workflow.biz.engine.service.FlowTaskService;
import com.taotao.cloud.workflow.biz.engine.util.ModelUtil;
import com.taotao.cloud.workflow.biz.form.entity.ViolationHandlingEntity;
import com.taotao.cloud.workflow.biz.form.mapper.ViolationHandlingMapper;
import com.taotao.cloud.workflow.biz.form.service.ViolationHandlingService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 违章处理申请表 */
@Service
public class ViolationHandlingServiceImpl extends ServiceImpl<ViolationHandlingMapper, ViolationHandlingEntity>
        implements ViolationHandlingService {

    @Autowired
    private BillRuleService billRuleService;

    @Autowired
    private FlowTaskService flowTaskService;

    @Override
    public ViolationHandlingEntity getInfo(String id) {
        QueryWrapper<ViolationHandlingEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViolationHandlingEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    @DSTransactional
    public void save(String id, ViolationHandlingEntity entity) throws WorkFlowException {
        // 表单信息
        if (id == null) {
            entity.setId(RandomUtil.uuId());
            this.save(entity);
            billRuleService.useBillNumber("WF_ViolationHandlingNo");
        } else {
            entity.setId(id);
            this.updateById(entity);
        }
        // 流程信息
        ModelUtil.save(
                id,
                entity.getFlowId(),
                entity.getId(),
                entity.getFlowTitle(),
                entity.getFlowUrgent(),
                entity.getBillNo(),
                entity);
    }

    @Override
    @DSTransactional
    public void submit(String id, ViolationHandlingEntity entity, Map<String, List<String>> candidateList)
            throws WorkFlowException {
        // 表单信息
        if (id == null) {
            entity.setId(RandomUtil.uuId());
            this.save(entity);
            billRuleService.useBillNumber("WF_ViolationHandlingNo");
        } else {
            entity.setId(id);
            this.updateById(entity);
        }
        // 流程信息
        ModelUtil.submit(
                id,
                entity.getFlowId(),
                entity.getId(),
                entity.getFlowTitle(),
                entity.getFlowUrgent(),
                entity.getBillNo(),
                entity,
                null,
                candidateList);
    }

    @Override
    public void data(String id, String data) {
        ViolationHandlingForm violationHandlingForm = JsonUtils.getJsonToBean(data, ViolationHandlingForm.class);
        ViolationHandlingEntity entity = JsonUtils.getJsonToBean(violationHandlingForm, ViolationHandlingEntity.class);
        entity.setId(id);
        this.saveOrUpdate(entity);
    }
}
