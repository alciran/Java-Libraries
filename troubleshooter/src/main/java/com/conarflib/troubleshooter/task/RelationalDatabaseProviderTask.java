package com.conarflib.troubleshooter.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.conarflib.database.relational.DataSourceConfig;
import com.conarflib.database.relational.RelationalDatabase;
import com.conarflib.troubleshooter.model.TaskStatus;
import com.conarflib.troubleshooter.model.TroubleshooterTask;

public class RelationalDatabaseProviderTask {

    public static TroubleshooterTask databaseStructureHost(String host) {
        if (host == null)
            return new TroubleshooterTask(TaskStatus.IGNORED, "No database host provided to task...");
        else {
            try {
                InetAddress.getByName(host);
                return new TroubleshooterTask(TaskStatus.SUCCESS, "Database host [ " + host + " ] is valid!");
            } catch (UnknownHostException unkEx) {
                return new TroubleshooterTask(TaskStatus.FAIL,
                        "Database host [ " + host + " ] invalid: " + unkEx.getMessage());
            }
        }
    }

    private static Connection getConnection(DataSourceConfig dataSourceConfig) throws SQLException {
        return RelationalDatabase.getInstance(dataSourceConfig).getConnection();
    }

    public static TroubleshooterTask checkConnection(DataSourceConfig dataSourceConfig) {
        if (dataSourceConfig == null)
            return new TroubleshooterTask(TaskStatus.IGNORED, "No DataSourceConfig provided to task...");
        else {
            try {
                Connection connection = getConnection(dataSourceConfig);
                connection.close();
                return new TroubleshooterTask(TaskStatus.SUCCESS,
                        "Database [ " + dataSourceConfig.getDriver() + " ] connected!");
            } catch (SQLException sqlEx) {
                return new TroubleshooterTask(TaskStatus.FAIL,
                        "Erro connection database [ " + dataSourceConfig.getDriver() + " ] : " + sqlEx.getSQLState()
                                + " => " + sqlEx.getErrorCode() + ", " + sqlEx.getMessage());
            }
        }
    }

    public static TroubleshooterTask getDatabaseObject(DataSourceConfig dataSourceConfig, String databaseObject) {
        if (dataSourceConfig == null || databaseObject == null)
            return new TroubleshooterTask(TaskStatus.IGNORED,
                    "No DatasourceConfig and/or database object provided to task...");
        else {
            try {
                Connection connection = getConnection(dataSourceConfig);
                DatabaseMetaData dbm = connection.getMetaData();
                ResultSet tables = dbm.getTables(null, null, databaseObject, new String[] { "TABLE", "VIEW" });
                if (tables.next()) {
                    connection.close();
                    return new TroubleshooterTask(TaskStatus.SUCCESS,
                            "Database Object [ " + databaseObject + " ] is available!");
                } else {
                    connection.close();
                    return new TroubleshooterTask(TaskStatus.FAIL,
                            "Database Object [ " + databaseObject + " ] not exists!");
                }
            } catch (SQLException sqlEx) {
                return new TroubleshooterTask(TaskStatus.FAIL, "Error check database object: " + sqlEx.getSQLState()
                        + " => " + sqlEx.getErrorCode() + ", " + sqlEx.getMessage());
            }
        }
    }

    public static TroubleshooterTask getSchema(DataSourceConfig dataSourceConfig, String schema) {
        if (dataSourceConfig == null || schema == null)
            return new TroubleshooterTask(TaskStatus.IGNORED,
                    "No DatasourceConfig and/or schema provided to task...");
        else {
            try {
                Connection connection = getConnection(dataSourceConfig);
                DatabaseMetaData dbm = connection.getMetaData();
                ResultSet schemas = dbm.getSchemas(null, schema);
                if (schemas.next()) {
                    connection.close();
                    return new TroubleshooterTask(TaskStatus.SUCCESS, "Database Schema [ " + schema + " ] available!");
                } else {
                    connection.close();
                    return new TroubleshooterTask(TaskStatus.FAIL, "Database Schema [ " + schema + " ] not exists!");
                }
            } catch (SQLException sqlEx) {
                return new TroubleshooterTask(TaskStatus.FAIL, "Erro on check database schema: " + sqlEx.getSQLState()
                        + " => " + sqlEx.getErrorCode() + ", " + sqlEx.getMessage());
            }
        }
    }

}
