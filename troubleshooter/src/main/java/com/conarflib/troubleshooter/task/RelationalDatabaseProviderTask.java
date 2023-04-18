package com.conarflib.troubleshooter.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import com.conarflib.database.relational.DataSourceConfig;
import com.conarflib.database.relational.RelationalDatabase;

public class RelationalDatabaseProviderTask {

    public static final TroubleshooterTask databaseStructureHost(String host) {
        String taskName = "Check host";
        if (host == null)
            return new TroubleshooterTask(taskName, TaskStatus.IGNORED, "No database host provided to task...");
        else {
            try {
                InetAddress.getByName(host);
                return new TroubleshooterTask(taskName, TaskStatus.SUCCESS,
                        "Database host [ " + host + " ] is valid!");
            } catch (UnknownHostException unkEx) {
                return new TroubleshooterTask(taskName, TaskStatus.FAIL,
                        "Database host [ " + host + " ] invalid: " + unkEx.getMessage());
            }
        }
    }

    public static TroubleshooterTask checkConnection(DataSourceConfig dataSourceConfig) {
        String taskName = "Check connection";
        if (dataSourceConfig == null)
            return new TroubleshooterTask(taskName, TaskStatus.IGNORED, "No DataSourceConfig provided to task...");
        else {
            try {
                Connection connection = RelationalDatabase.getInstance(dataSourceConfig).getConnection();
                connection.close();
                return new TroubleshooterTask(taskName, TaskStatus.SUCCESS,
                        "Database [ " + dataSourceConfig.getDriver() + " ] connected!");
            } catch (Exception sqlEx) {
                return new TroubleshooterTask(taskName, TaskStatus.FAIL,
                        "Erro connection database [ " + sqlEx.getMessage() + " ]");
            }
        }
    }

    public static TroubleshooterTask getDatabaseObject(DataSourceConfig dataSourceConfig, String databaseObject) {
        String taskName = "Check object";
        if (dataSourceConfig == null || databaseObject == null)
            return new TroubleshooterTask(taskName, TaskStatus.IGNORED,
                    "No DatasourceConfig and/or database object provided to task...");
        else {
            try {
                Connection connection = RelationalDatabase.getInstance(dataSourceConfig).getConnection();
                DatabaseMetaData dbm = connection.getMetaData();
                ResultSet tables = dbm.getTables(null, null, databaseObject, new String[] { "TABLE", "VIEW" });
                if (tables.next()) {
                    connection.close();
                    return new TroubleshooterTask(taskName, TaskStatus.SUCCESS,
                            "Database Object [ " + databaseObject + " ] is available!");
                } else {
                    connection.close();
                    return new TroubleshooterTask(taskName, TaskStatus.FAIL,
                            "Database Object [ " + databaseObject + " ] not exists!");
                }
            } catch (Exception sqlEx) {
                return new TroubleshooterTask(taskName, TaskStatus.FAIL,
                        "Error check database object: " + sqlEx.getMessage());
            }
        }
    }

    public static TroubleshooterTask getSchema(DataSourceConfig dataSourceConfig, String schema) {
        String taskName = "Check schema";
        if (dataSourceConfig == null || schema == null)
            return new TroubleshooterTask(taskName, TaskStatus.IGNORED,
                    "No DatasourceConfig and/or schema provided to task...");
        else {
            try {
                Connection connection = RelationalDatabase.getInstance(dataSourceConfig).getConnection();
                DatabaseMetaData dbm = connection.getMetaData();
                ResultSet schemas = dbm.getSchemas(null, schema);
                if (schemas.next()) {
                    connection.close();
                    return new TroubleshooterTask(taskName, TaskStatus.SUCCESS,
                            "Database Schema [ " + schema + " ] available!");
                } else {
                    connection.close();
                    return new TroubleshooterTask(taskName, TaskStatus.FAIL,
                            "Database Schema [ " + schema + " ] not exists!");
                }
            } catch (Exception sqlEx) {
                return new TroubleshooterTask(taskName, TaskStatus.FAIL,
                        "Erro on check database schema: " + sqlEx.getMessage());
            }
        }
    }

}
