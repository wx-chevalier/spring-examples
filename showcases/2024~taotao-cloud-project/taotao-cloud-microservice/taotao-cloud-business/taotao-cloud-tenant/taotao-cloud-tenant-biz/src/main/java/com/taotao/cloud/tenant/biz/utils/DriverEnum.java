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

package com.taotao.cloud.tenant.biz.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zuihou
 */
@Slf4j
public enum DriverEnum {

    /** mysql */
    MYSQL("com.mysql.cj.jdbc.Driver"),

    /** oracle */
    ORACLE("oracle.jdbc.driver.OracleDriver"),

    /** sql server */
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    ;

    /** 驱动 */
    private final String driver;

    DriverEnum(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
