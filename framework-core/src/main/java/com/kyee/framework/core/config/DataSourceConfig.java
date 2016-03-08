package com.kyee.framework.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyee.framework.core.data.flyway.FlywayMigration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 程峰
 * 创建时间:15/9/11 上午11:22
 * 任务号:MOBILEDEVELOP-10321
 * 创建说明:数据源初始化配置
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig implements BeanDefinitionRegistryPostProcessor,ApplicationContextAware,EnvironmentAware {

    private Environment environment;

    private ApplicationContext applicationContext;

    /**
     * 数据源配置
     */
    private ArrayList<DataSourceProperty> dataSourceProperties = new ArrayList<>();

    private final static Log LOG = LogFactory.getLog(DataSourceConfig.class);


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment =environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder beanDefinitionBuilder;
        readConfig();
        if(0 == dataSourceProperties.size()){
            LOG.error("没有数据源");
            return;
        }
        int i=0;
        for(DataSourceProperty dataSourceProperty:dataSourceProperties){
            String beanName = dataSourceProperty.getDbName();
            String sqlSessionFactoryBeanName = "sqlFactoryOf"+beanName;
            String mapperScanBeanName = "mapperScanOf"+beanName;
            String flywayBeanName = "flywayOf"+beanName;
            String txManagerName = dataSourceProperty.getTxManager();
            boolean isEnabledFlyway = false;

            //register dataSource
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DruidDataSource.class);
            setDataSourceProperty(beanDefinitionBuilder, dataSourceProperty,i);
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            DruidDataSource datasource = (DruidDataSource)applicationContext.getBean(beanName);

            //register flyway if flywaySqlLocation is not null
            if(dataSourceProperty.getFlywaySqlLocation() != null) {
                isEnabledFlyway = true;
                beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(FlywayMigration.class);
                beanDefinitionBuilder.addPropertyValue("dataSource", datasource).
                        addPropertyValue("flywaySqlLocation",dataSourceProperty.getFlywaySqlLocation());
                beanDefinitionBuilder.setInitMethodName("migrate");
                registry.registerBeanDefinition(flywayBeanName,beanDefinitionBuilder.getBeanDefinition());
            }

            //register mybatis SqlSessionFactory
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SqlSessionFactoryBean.class);
            beanDefinitionBuilder.addPropertyValue("dataSource", datasource);
            if(isEnabledFlyway){    //如果启动flyway, 设置依赖。因为flyway启动完毕才能进行数据库操作。
                beanDefinitionBuilder.addDependsOn(flywayBeanName);
            }
            registry.registerBeanDefinition(sqlSessionFactoryBeanName, beanDefinitionBuilder.getBeanDefinition());

            //register mybatis MapperScannerConfigurer
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(MapperScannerConfigurer.class);
            beanDefinitionBuilder.addPropertyValue("sqlSessionFactoryBeanName", sqlSessionFactoryBeanName);
            beanDefinitionBuilder.addPropertyValue("basePackage", dataSourceProperty.getBasePackage());
            if(isEnabledFlyway){    //如果启动flyway, 设置依赖。因为flyway启动完毕才能进行数据库操作。
                beanDefinitionBuilder.addDependsOn(flywayBeanName);
            }
            registry.registerBeanDefinition(mapperScanBeanName, beanDefinitionBuilder.getBeanDefinition());

            //register DataSourceTransactionManager
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DataSourceTransactionManager.class);
            beanDefinitionBuilder.addPropertyValue("dataSource",datasource);
            if(i==0){   //将第一个数据源的事务管理器设为primary
                beanDefinitionBuilder.getBeanDefinition().setPrimary(true);
            }
            if(isEnabledFlyway){    //如果启动flyway, 设置依赖。因为flyway启动完毕才能进行数据库操作。
                beanDefinitionBuilder.addDependsOn(flywayBeanName);
            }
            registry.registerBeanDefinition(txManagerName,beanDefinitionBuilder.getBeanDefinition());
            ++i;
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    /**
     * 设置数据源属性
     * @param beanDefinitionBuilder beanDefinitionBuilder,组建BeanDefinition工具
     * @param dataSourceProperty 数据源属性
     * @param i 当前是第几个数据源，第0个数据源将会被设为首选数据源
     */
    private void setDataSourceProperty(BeanDefinitionBuilder beanDefinitionBuilder,DataSourceProperty dataSourceProperty,int i){
        beanDefinitionBuilder
                .addPropertyValue("username", dataSourceProperty.getUsername())
                .addPropertyValue("password", dataSourceProperty.getPassword())
                .addPropertyValue("url", dataSourceProperty.getUrl())
        ;
        if(dataSourceProperty.getDriverClassName() != null){
            beanDefinitionBuilder.addPropertyValue("driverClassName",dataSourceProperty.getDriverClassName());
        }
        if(dataSourceProperty.getMaxActive() != -1){
            beanDefinitionBuilder.addPropertyValue("maxActive",dataSourceProperty.getMaxActive());
        }
        if(i == 0) {    //将第一个数据源设为primary
            beanDefinitionBuilder.getBeanDefinition().setPrimary(true);
        }
    }

    /**
     * 读取配置文件
     */
    private void readConfig(){
        String profile = (environment.getProperty("spring.profiles.active")== null)?"default":environment.getProperty("spring.profiles.active");
        Yaml yaml = new Yaml();
        Resource resource =  applicationContext.getResource("classpath:application.yml");
        try {
            String currentProfile = "default";
            Object tmp;
            for(Object object:yaml.loadAll(resource.getInputStream())){
                if(((tmp = ((Map)object).get("spring")) != null) && ((tmp = ((Map)tmp).get("profiles")) != null) && !(tmp instanceof LinkedHashMap)){
                    currentProfile = (String) tmp;
                }
                if(profile.equals(currentProfile)){
                    if(((tmp = ((Map)object).get("kyee")) == null) || ((tmp = ((Map)tmp).get("datasource")) ==null) || ((tmp = ((Map)tmp).get("config"))==null)){
                        LOG.error("没有检测到数据源配置:kyee.datasource.config");
                        return;
                    }else {
                        this.dataSourceProperties = new ObjectMapper().convertValue(tmp, new TypeReference<ArrayList<DataSourceProperty>>() {
                        });
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("解析配置文件错误",e);
        }
    }
}
