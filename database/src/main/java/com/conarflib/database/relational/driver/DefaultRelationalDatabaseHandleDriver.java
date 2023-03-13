package com.conarflib.database.relational.driver;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.conarflib.database.relational.DataSourceConfig;
import com.conarflib.database.relational.datasource.DefaultHikariDataSource;

public class DefaultRelationalDatabaseHandleDriver implements RelationalDatabaseConnection {

    private DataSourceConfig dataSourceConfig;

    public DefaultRelationalDatabaseHandleDriver(DataSourceConfig dataSourceConfig){
        this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public DataSource getDataSource() throws SQLException {

        DefaultHikariDataSource hikariDataSource = new DefaultHikariDataSource();
        String driver = dataSourceConfig.getDriver();
        
        if(driver.equals("PostgreSQL"))
            return hikariDataSource.getDataSourceByClassName(dataSourceConfig,
                "org.postgresql.ds.PGSimpleDataSource");
        else if(driver.equals("OracleDB"))
            return hikariDataSource.getDataSourceByClassName(dataSourceConfig,
                "oracle.jdbc.driver.OracleDriver");
        else if(driver.equals("CacheDB"))
            return hikariDataSource.getDataSourceByJdbcUrl(dataSourceConfig,
                "jdbc:Cache://" + dataSourceConfig.getHost() + ":" + dataSourceConfig.getPortNumber() + "/" + dataSourceConfig.getDatabase());
        else if(driver.equals("MySQL") || driver.equals("MariaDB"))
            return hikariDataSource.getDataSourceByClassName(dataSourceConfig,
                "com.mysql.cj.jdbc.MysqlDataSource");
        else throw new SQLException("Unknow database driver [ " + dataSourceConfig.getDriver() + " ]");
    }
    
}
