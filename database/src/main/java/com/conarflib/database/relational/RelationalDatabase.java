package com.conarflib.database.relational;

import java.sql.Connection;
import java.sql.SQLException;
import com.conarflib.database.relational.driver.DefaultRelationalDatabaseHandleDriver;
import com.conarflib.util.ObjectUtils;

public class RelationalDatabase {

    private Connection connection;
    private DataSourceConfig dataSourceConfig;
    private static RelationalDatabase instance;

    public RelationalDatabase(DataSourceConfig dataSourceConfig) throws SQLException{
        ObjectUtils.requiredNonNull(dataSourceConfig, "The DataSourceConfig must not be null!");
        this.dataSourceConfig = dataSourceConfig;
        this.setConnection();
    }

    private void setConnection() throws SQLException{
        DefaultRelationalDatabaseHandleDriver driver = new DefaultRelationalDatabaseHandleDriver(this.dataSourceConfig);
        this.connection = driver.getConnection();
    }

    /**
     * Class to get a Connection. Return a connection based on driver name of the obejct Datource Config
     */
    public Connection getConnection(){
        return this.connection;
    }

    /**
    * Singleton method to return a single instance of this class.
    * @param datasourceConfig DatasourceConfig with database datasource configurantion.
    * @return RelationalDatabaseConection instance.
    * @throws SQLException
    */
    public static RelationalDatabase getInstance(DataSourceConfig dataSourceConfig) throws SQLException {        
        if(instance == null)
            instance = new RelationalDatabase(dataSourceConfig);
        else if(instance.getConnection().isClosed())
            instance = new RelationalDatabase(dataSourceConfig);

        return instance;
    }
    
}
