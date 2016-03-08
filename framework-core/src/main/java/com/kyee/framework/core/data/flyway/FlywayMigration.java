package com.kyee.framework.core.data.flyway;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

/**
 * @author 程峰
 * 创建时间:15/9/10 上午11:05
 * 任务号:MOBILEDEVELOP-10321
 * 创建说明:flyway初始化类，init方法需设为migrate
 */
public class FlywayMigration {

    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * sql文件保存路径
     */
    private String flywaySqlLocation;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getFlywaySqlLocation() {
        return flywaySqlLocation;
    }

    public void setFlywaySqlLocation(String flywaySqlLocation) {
        this.flywaySqlLocation = flywaySqlLocation;
    }

    public void migrate(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations(flywaySqlLocation);
        flyway.setSqlMigrationPrefix("");
        flyway.setSqlMigrationSeparator("_");
        flyway.setSqlMigrationSuffix(".SQL");
        flyway.setBaselineOnMigrate(true);
        flyway.setValidateOnMigrate(true);
        flyway.repair();
        flyway.migrate();
    }


}
