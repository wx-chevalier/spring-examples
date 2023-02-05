package com.fc.v2.common.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.fc.v2.common.dataSources.DataSourceType;
import com.fc.v2.common.dataSources.DynamicDataSource;
import com.fc.v2.shiro.util.ShiroUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Mybatis多数据源配置
 * 参考文章：https://www.cnblogs.com/geekdc/p/10963476.html
 *
 * @author fuce
 * @ClassName: MybatisPlusConfig
 * @date 2019-12-06 21:11
 */
@Configuration
@MapperScan(basePackages = {"com.fc.v2.mapper.auto", "com.fc.v2.mapper.custom"})
public class MybatisPlusConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DruidDataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("mybatis-plus.configuration")
    public MybatisConfiguration mybatisConfiguration() {
        return new MybatisConfiguration();
    }

    @Bean
    @ConfigurationProperties("mybatis-plus.global-config")
    public GlobalConfig globalConfig() {
        return new GlobalConfig().setMetaObjectHandler(new MetaObjectHandler() {
        	
        	/**
        	 * 自动填充
        	 */
            @Override
            public void insertFill(MetaObject metaObject) {
                this.fillStrategy(metaObject, "createBy", ShiroUtils.getLoginName());
                this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
            }
            
            /**
             * 自动填充
             */
            @Override
            public void updateFill(MetaObject metaObject) {
                this.fillStrategy(metaObject, "updateBy", ShiroUtils.getLoginName());
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            }
        });
    }

    @Autowired
    private MybatisConfiguration mybatisConfiguration;

    @Autowired
    private GlobalConfig globalConfig;

    @Value("${mybatis-plus.mapper-locations}")
    private String locationPattern;

    @Value("${mybatis-plus.type-aliases-package}")
    private String typeAliasesPackage;

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
        return new DynamicDataSource(masterDataSource(), targetDataSources);
    }

    /**
     * 手动注入Mybatis-plus, 暂不能自动注入
     * 
     * @param dynamicDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);
        // 设置mapper.xml的位置路径
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(locationPattern);
        factoryBean.setMapperLocations(resources);
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        factoryBean.setConfiguration(mybatisConfiguration);
        factoryBean.setGlobalConfig(globalConfig);
        return factoryBean.getObject();
    }

    /**
     * 配置@Transactional注解事务
     *
     * @param dynamicDataSource
     * @return
     * @author fuce
     * @Date 2019年12月7日 上午11:31:33
     */
    @Bean
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
