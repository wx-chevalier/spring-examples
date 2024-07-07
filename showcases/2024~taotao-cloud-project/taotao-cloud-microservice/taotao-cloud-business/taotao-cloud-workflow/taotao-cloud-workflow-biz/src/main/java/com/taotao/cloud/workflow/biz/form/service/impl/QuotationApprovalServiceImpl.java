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
import com.taotao.cloud.workflow.biz.common.model.form.quotationapproval.QuotationApprovalForm;
import com.taotao.cloud.workflow.biz.engine.service.FlowTaskService;
import com.taotao.cloud.workflow.biz.engine.util.ModelUtil;
import com.taotao.cloud.workflow.biz.form.entity.QuotationApprovalEntity;
import com.taotao.cloud.workflow.biz.form.mapper.QuotationApprovalMapper;
import com.taotao.cloud.workflow.biz.form.service.QuotationApprovalService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 报价审批表 */
@Service
public class QuotationApprovalServiceImpl extends ServiceImpl<QuotationApprovalMapper, QuotationApprovalEntity>
        implements QuotationApprovalService {

    @Autowired
    private BillRuleService billRuleService;

    @Autowired
    private FlowTaskService flowTaskService;

    @Autowired
    private FileManageUtil fileManageUtil;

    @Override
    public QuotationApprovalEntity getInfo(String id) {
        QueryWrapper<QuotationApprovalEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(QuotationApprovalEntity::getId, id);
        return this.getOne(queryWrapper);
    }

    @Override
    @DSTransactional
    public void save(String id, QuotationApprovalEntity entity) throws WorkFlowException {
        // 表单信息
        if (id == null) {
            entity.setId(RandomUtil.uuId());
            this.save(entity);
            billRuleService.useBillNumber("WF_QuotationApprovalNo");
            // 添加附件
            List<FileModel> data = JsonUtils.getJsonToList(entity.getFileJson(), FileModel.class);
            fileManageUtil.createFile(data);
        } else {
            entity.setId(id);
            this.updateById(entity);
            // 更新附件
            List<FileModel> data = JsonUtils.getJsonToList(entity.getFileJson(), FileModel.class);
            fileManageUtil.updateFile(data);
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
    public void submit(String id, QuotationApprovalEntity entity, Map<String, List<String>> candidateList)
            throws WorkFlowException {
        // 表单信息
        if (id == null) {
            entity.setId(RandomUtil.uuId());
            this.save(entity);
            billRuleService.useBillNumber("WF_QuotationApprovalNo");
            // 添加附件
            List<FileModel> data = JsonUtils.getJsonToList(entity.getFileJson(), FileModel.class);
            fileManageUtil.createFile(data);
        } else {
            entity.setId(id);
            this.updateById(entity);
            // 更新附件
            List<FileModel> data = JsonUtils.getJsonToList(entity.getFileJson(), FileModel.class);
            fileManageUtil.updateFile(data);
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
        QuotationApprovalForm quotationApprovalForm = JsonUtils.getJsonToBean(data, QuotationApprovalForm.class);
        QuotationApprovalEntity entity = JsonUtils.getJsonToBean(quotationApprovalForm, QuotationApprovalEntity.class);
        entity.setId(id);
        this.saveOrUpdate(entity);
    }
}
