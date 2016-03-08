package com.kyee.framework.mutidb.jta.config;

import com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyee.framework.core.data.flyway.FlywayMigration;
import com.kyee.framework.mutidb.jta.domain.DataSourceProperty;
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
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 程峰
 * 创建时间:15/8/27 下午3:46
 * 任务号: MOBILEDEVELOP-10187
 * 创建说明: 动态配置数据源
 */
@Configuration
public class DataSourceConfiguration implements BeanDefinitionRegistryPostProcessor,ApplicationContextAware,EnvironmentAware {


    private Environment environment;

    private ApplicationContext applicationContext;

    private ArrayList<DataSourceProperty> dataSourceProperties = new ArrayList<>();

    private final static Log LOG = LogFactory.getLog(DataSourceConfiguration.class);
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }



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

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        BeanDefinitionBuilder beanDefinitionBuilder;
        readConfig();
        if(0 == dataSourceProperties.size()){
            LOG.error("没有数据源");
            return;
        }
        int i=0;
        for(DataSourceProperty dataSourceProperty:dataSourceProperties){
            String beanName = dataSourceProperty.getUniqueResourceName();
            String sqlSessionFactoryBeanName = "sqlFactoryOf"+beanName;
            String mapperScanBeanName = "mapperScanOf"+beanName;
            String flywayBeanName = "flywayOf"+beanName;
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(AtomikosNonXADataSourceBean.class);
            setDataSourceProperty(beanDefinitionBuilder, dataSourceProperty,i);
            i++;
            //register dataSource
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            AtomikosNonXADataSourceBean datasource = (AtomikosNonXADataSourceBean)applicationContext.getBean(beanName);

            //register flyway if flywaySqlLocation is not null
            if(dataSourceProperty.getFlywaySqlLocation() != null) {
                beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(FlywayMigration.class);
                beanDefinitionBuilder.addPropertyValue("dataSource", datasource).
                        addPropertyValue("flywaySqlLocation",dataSourceProperty.getFlywaySqlLocation());
                beanDefinitionBuilder.setInitMethodName("migrate");
                beanDefinitionRegistry.registerBeanDefinition(flywayBeanName,beanDefinitionBuilder.getBeanDefinition());
            }

            //register mybatis SqlSessionFactory
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(SqlSessionFactoryBean.class);
            beanDefinitionBuilder.addPropertyValue("dataSource", datasource);
            beanDefinitionRegistry.registerBeanDefinition(sqlSessionFactoryBeanName, beanDefinitionBuilder.getBeanDefinition());

            //register mybatis MapperScannerConfigurer
            beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(MapperScannerConfigurer.class);
            beanDefinitionBuilder.addPropertyValue("sqlSessionFactoryBeanName", sqlSessionFactoryBeanName);
            beanDefinitionBuilder.addPropertyValue("basePackage", dataSourceProperty.getBasePackage());
            beanDefinitionRegistry.registerBeanDefinition(mapperScanBeanName, beanDefinitionBuilder.getBeanDefinition());
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private void setDataSourceProperty(BeanDefinitionBuilder beanDefinitionBuilder,DataSourceProperty dataSourceProperty,int i){
        beanDefinitionBuilder
                .addPropertyValue("uniqueResourceName", dataSourceProperty.getUniqueResourceName())
                .addPropertyValue("driverClassName", getDataSourceClassName(dataSourceProperty.getDataSourceType()))
                .addPropertyValue("url", dataSourceProperty.getUrl())
                .addPropertyValue("user", dataSourceProperty.getUsername())
                .addPropertyValue("password", dataSourceProperty.getPassword())
                .addPropertyValue("minPoolSize", dataSourceProperty.getMinPoolSize())
                .addPropertyValue("maxPoolSize", dataSourceProperty.getMaxPoolSize())
                .addPropertyValue("testQuery", dataSourceProperty.getTestQuery())
                .addPropertyValue("borrowConnectionTimeout", dataSourceProperty.getBorrowConnectionTimeout())
                .addPropertyValue("reapTimeout", dataSourceProperty.getReapTimeout())
                .addPropertyValue("maxIdleTime", dataSourceProperty.getMaxIdleTime())
                .addPropertyValue("maintenanceInterval", dataSourceProperty.getMaintenanceInterval())
                .addPropertyValue("loginTimeout", dataSourceProperty.getLoginTimeout())
                .setInitMethodName("init")
                .setDestroyMethodName("close");
        if(i == 0) {
            beanDefinitionBuilder.getBeanDefinition().setPrimary(true);
        }
    }

    private String getDataSourceClassName(String string){
        switch (string){
            case "mysql":
                return "com.mysql.jdbc.Driver";
            case "oracle":
                return "oracle.jdbc.driver.OracleDriver";
            case "sqlServer":
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            default:
                return string;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
