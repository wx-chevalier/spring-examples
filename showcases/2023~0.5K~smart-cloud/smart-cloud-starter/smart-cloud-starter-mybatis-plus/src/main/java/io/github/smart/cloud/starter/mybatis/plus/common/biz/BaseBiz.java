/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.smart.cloud.starter.mybatis.plus.common.biz;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.smart.cloud.common.pojo.BaseEntityResponse;
import io.github.smart.cloud.common.pojo.BasePageRequest;
import io.github.smart.cloud.common.pojo.BasePageResponse;
import io.github.smart.cloud.starter.mybatis.plus.common.entity.BaseEntity;
import io.github.smart.cloud.starter.mybatis.plus.common.mapper.SmartMapper;
import io.github.smart.cloud.starter.mybatis.plus.enums.DeleteState;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * biz基类
 *
 * @author collin
 * @date 2021-10-31
 */
public class BaseBiz<M extends SmartMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> {

    /**
     * in-line式批量插入
     *
     * @param entityList
     * @return
     */
    public Integer insertBatchSomeColumn(List<T> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @param uid
     * @return
     */
    public Boolean logicDelete(Long id, Long uid) {
        T entity = BeanUtils.instantiateClass(entityClass);
        entity.setId(id);
        entity.setDelUser(uid);
        entity.setDelTime(new Date());
        entity.setDelState(DeleteState.DELETED);
        return baseMapper.updateById(entity) == 1;
    }

    /**
     * 清空表数据
     */
    public void truncate() {
        baseMapper.truncate();
    }

    /**
     * 分页查询表字段信息
     *
     * @param q
     * @param wrapper
     * @param pageItemClass
     * @param <R>
     * @param <Q>
     * @return
     */
    public <R extends BaseEntityResponse, Q extends BasePageRequest> BasePageResponse<R> page(Q q, Wrapper<T> wrapper, Class<R> pageItemClass) {
        IPage<T> page = super.page(new Page<>(q.getPageNum(), q.getPageSize(), true), wrapper);
        List<T> entityDatas = page.getRecords();
        if (CollectionUtils.isEmpty(entityDatas)) {
            return new BasePageResponse<>(null, q.getPageNum(), q.getPageSize(), 0);
        }

        List<R> pageDatas = entityDatas.stream()
                .map(entity -> {
                    R r = BeanUtils.instantiateClass(pageItemClass);
                    BeanUtils.copyProperties(entity, r);
                    return r;
                }).collect(Collectors.toList());

        return new BasePageResponse<>(pageDatas, q.getPageNum(), q.getPageSize(), page.getTotal());
    }

}