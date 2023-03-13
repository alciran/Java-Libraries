package com.conarflib.database.relational.datasource;

import javax.sql.DataSource;

import com.conarflib.database.relational.DataSourceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DefaultHikariDataSource {
    
    private HikariDataSource getHikariDataSource(HikariConfig configHikari){
        HikariDataSource dataSource = new HikariDataSource(configHikari);
        dataSource.setMinimumIdle(1);
        dataSource.setMaxLifetime(900000);
        dataSource.setMaximumPoolSize(1);
        dataSource.setIdleTimeout(300000);
        dataSource.setConnectionTimeout(30000);

        return dataSource;
    }


    public DataSource getDataSourceByJdbcUrl(DataSourceConfig dataSourceConfig, String jdbcUrl){
        HikariConfig configHikari = new HikariConfig();
        configHikari.setJdbcUrl(jdbcUrl);
        configHikari.addDataSourceProperty("user", dataSourceConfig.getUser());
        configHikari.addDataSourceProperty("password", dataSourceConfig.getPassword());

        return getHikariDataSource(configHikari);        
    }

    public DataSource getDataSourceByClassName(DataSourceConfig dataSourceConfig, String className){
        HikariConfig configHikari = new HikariConfig();
        configHikari.setDataSourceClassName(className);
        configHikari.addDataSourceProperty("serverName", dataSourceConfig.getHost());
        configHikari.addDataSourceProperty("portNumber", dataSourceConfig.getPortNumber());
        configHikari.addDataSourceProperty("databaseName", dataSourceConfig.getDatabase());
        configHikari.addDataSourceProperty("user", dataSourceConfig.getUser());
        configHikari.addDataSourceProperty("password", dataSourceConfig.getPassword());

        return getHikariDataSource(configHikari);        
    }
}
