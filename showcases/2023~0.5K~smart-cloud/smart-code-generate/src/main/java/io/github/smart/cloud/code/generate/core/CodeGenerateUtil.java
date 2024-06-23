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
package io.github.smart.cloud.code.generate.core;

import io.github.smart.cloud.code.generate.bo.ColumnMetaDataBO;
import io.github.smart.cloud.code.generate.bo.TableMetaDataBO;
import io.github.smart.cloud.code.generate.bo.template.BaseMapperBO;
import io.github.smart.cloud.code.generate.bo.template.BaseRespBO;
import io.github.smart.cloud.code.generate.bo.template.ClassCommentBO;
import io.github.smart.cloud.code.generate.bo.template.EntityBO;
import io.github.smart.cloud.code.generate.properties.CodeProperties;
import io.github.smart.cloud.code.generate.properties.PathProperties;
import io.github.smart.cloud.code.generate.properties.YamlProperties;
import io.github.smart.cloud.code.generate.util.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码生成工具类
 *
 * @author collin
 * @date 2019-07-15
 */
public class CodeGenerateUtil {

    private CodeGenerateUtil() {
    }

    /**
     * 生成代码
     *
     * @throws Exception
     */
    public static void init() throws Exception {
        init(YamlUtil.getYamlPath());
    }

    /**
     * 生成代码
     *
     * @param yamlPath
     * @throws Exception
     */
    public static void init(String yamlPath) throws Exception {
        YamlProperties yamlProperties = YamlUtil.readYamlProperties(yamlPath);
        YamlPropertiesCheckUtil.check(yamlProperties);

        CodeProperties codeProperties = yamlProperties.getCode();
        try (Connection connnection = DbUtil.getConnection(yamlProperties.getDb());) {
            Map<String, TableMetaDataBO> tableMetaDataMap = DbUtil.getTablesMetaData(connnection, codeProperties);
            ClassCommentBO classComment = TemplateUtil.getClassCommentBO(codeProperties.getAuthor());

            for (Map.Entry<String, TableMetaDataBO> entry : tableMetaDataMap.entrySet()) {
                generateSingleTable(connnection.getCatalog(), entry.getValue(), connnection, classComment, codeProperties);
            }
        }
    }

    /**
     * 生成当个表的代码
     *
     * @param database      数据库名
     * @param tableMetaData
     * @param connnection
     * @param classComment  公共信息
     * @param code
     * @throws Exception
     */
    private static void generateSingleTable(String database, TableMetaDataBO tableMetaData, Connection connnection,
                                            ClassCommentBO classComment, CodeProperties code) throws Exception {
        List<ColumnMetaDataBO> columnMetaDatas = DbUtil.getTableColumnMetaDatas(connnection, database,
                tableMetaData.getName());
        String mainClassPackage = code.getMainClassPackage();
        PathProperties pathProperties = code.getProject().getPath();
        String rpcPath = pathProperties.getRpc();
        String servicePath = pathProperties.getService();
        Set<String> encryptFields = TableUtil.getEncryptFields(tableMetaData.getName(), code);

        EntityBO entityBO = TemplateUtil.getEntityBO(tableMetaData, columnMetaDatas, classComment, mainClassPackage,
                code.getMask(), encryptFields);
        CodeFileGenerateUtil.generateEntity(entityBO, servicePath);

        BaseRespBO baseResp = TemplateUtil.getBaseRespBodyBO(tableMetaData, columnMetaDatas, classComment, mainClassPackage,
                entityBO.getImportPackages(), code.getMask());
        CodeFileGenerateUtil.generateBaseRespVO(baseResp, rpcPath);

        BaseMapperBO baseMapperBO = TemplateUtil.getBaseMapperBO(tableMetaData, entityBO, classComment, mainClassPackage);
        CodeFileGenerateUtil.generateBaseMapper(baseMapperBO, servicePath);
    }

}