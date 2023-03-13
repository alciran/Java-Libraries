package com.conarflib.database.relational.driver;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface RelationalDatabaseConnection {
    
    DataSource getDataSource() throws SQLException;
    
    default Connection getConnection() throws SQLException{
        return this.getDataSource().getConnection();        
    }
    
}
