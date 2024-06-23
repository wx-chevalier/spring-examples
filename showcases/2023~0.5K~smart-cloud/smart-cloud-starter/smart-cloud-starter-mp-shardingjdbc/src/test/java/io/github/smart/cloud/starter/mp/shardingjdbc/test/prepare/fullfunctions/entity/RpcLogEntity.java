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
package io.github.smart.cloud.starter.mp.shardingjdbc.test.prepare.fullfunctions.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import io.github.smart.cloud.starter.mybatis.plus.common.entity.BaseEntity;

/**
 * 订单信息
 *
 * @author collin
 * @date 2021-03-23
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_rpc_log")
public class RpcLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 接口描述
     */
    @TableField(value = "f_api_desc")
    private String apiDesc;

}