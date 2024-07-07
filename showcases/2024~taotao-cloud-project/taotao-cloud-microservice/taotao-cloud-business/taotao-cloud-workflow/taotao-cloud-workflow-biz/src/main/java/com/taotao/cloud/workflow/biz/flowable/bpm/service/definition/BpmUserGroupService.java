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

package com.taotao.cloud.workflow.biz.flowable.bpm.service.definition;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.definition.vo.group.BpmUserGroupCreateReqVO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.definition.vo.group.BpmUserGroupPageReqVO;
import com.taotao.cloud.flowable.biz.bpm.controller.admin.definition.vo.group.BpmUserGroupUpdateReqVO;
import com.taotao.cloud.flowable.biz.bpm.dal.dataobject.definition.BpmUserGroupDO;
import java.util.*;
import jakarta.validation.*;

/**
 * 用户组 Service 接口
 *
 * @author 芋道源码
 */
public interface BpmUserGroupService {

    /**
     * 创建用户组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserGroup(@Valid BpmUserGroupCreateReqVO createReqVO);

    /**
     * 更新用户组
     *
     * @param updateReqVO 更新信息
     */
    void updateUserGroup(@Valid BpmUserGroupUpdateReqVO updateReqVO);

    /**
     * 删除用户组
     *
     * @param id 编号
     */
    void deleteUserGroup(Long id);

    /**
     * 获得用户组
     *
     * @param id 编号
     * @return 用户组
     */
    BpmUserGroupDO getUserGroup(Long id);

    /**
     * 获得用户组列表
     *
     * @param ids 编号
     * @return 用户组列表
     */
    List<BpmUserGroupDO> getUserGroupList(Collection<Long> ids);

    /**
     * 获得指定状态的用户组列表
     *
     * @param status 状态
     * @return 用户组列表
     */
    List<BpmUserGroupDO> getUserGroupListByStatus(Integer status);

    /**
     * 获得用户组分页
     *
     * @param pageReqVO 分页查询
     * @return 用户组分页
     */
    PageResult<BpmUserGroupDO> getUserGroupPage(BpmUserGroupPageReqVO pageReqVO);

    /**
     * 校验用户组们是否有效。如下情况，视为无效： 1. 用户组编号不存在 2. 用户组被禁用
     *
     * @param ids 用户组编号数组
     */
    void validUserGroups(Set<Long> ids);
}
