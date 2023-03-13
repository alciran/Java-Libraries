package com.conarflib.database.relational;

import lombok.Getter;

@Getter
public class DataSourceConfig {

    private String host;
    private String portNumber;
    private String user;
    private String password;
    private String database;

    private String driver;
    private String schema;
    private String charset;

    /**
     * Default constructor to DataSourceConfig based on configuration files.
     * @param driver Driver of Database
     * @param host Server Database host
     * @param portNumber Port number of Database service
     * @param user User to access database
     * @param password Password of user
     * @param database Database Name
     */
    public DataSourceConfig(String driver, String host, String portNumber, String user, String password, String database) {
        this.setDriver(driver);
        this.setHost(host);
        this.setPortNumber(portNumber);
        this.setUser(user);
        this.setPassword(password);
        this.setDatabase(database);
    }

    /**
     * Constructor to DataSourceConfig based on configuration files.
     * @param driver Driver of Database
     * @param host Server Database host
     * @param portNumber Port number of Database service
     * @param user User to access database
     * @param password Password of user
     * @param database Database Name
     * @param schema Schema of Database
     */
    public DataSourceConfig(String driver, String host, String portNumber, String user, String password, String database, String schema) {
        this.setDriver(driver);
        this.setHost(host);
        this.setPortNumber(portNumber);
        this.setUser(user);
        this.setPassword(password);
        this.setDatabase(database);
        this.setSchema(schema);
    }

    /**
     * Get the Port Number Integer.
     * @return Integer Port Number
     */
    public int getPortNumberInt(){
        return Integer.valueOf(this.getPortNumber());
    }

    public void setHost(String host) {
        if(host == null || host.equals(""))
            throw new NullPointerException("[ host ] cannot be empty or null => " + this.getClass().getName());
        else
            this.host = host;
    }

    public void setPortNumber(String portNumber) {
        if(portNumber == null || portNumber.equals(""))
            throw new NullPointerException("[ portNumber ] cannot be empty or null => " + this.getClass().getName());
        else
            this.portNumber = portNumber;
    }

    public void setUser(String user) {
        if(user == null || user.equals(""))
            throw new NullPointerException("[ user ] cannot be empty or null => " + this.getClass().getName());
        else
            this.user = user;
    }

    public void setPassword(String password) {
        if(password == null)
            throw new NullPointerException("[ password ] cannot be empty => " + this.getClass().getName());
        else
            this.password = password;
    }

    public void setDatabase(String database) {
        if(database == null || database.equals(""))
            throw new NullPointerException("[ database ] cannot be empty or null => " + this.getClass().getName());
        else
            this.database = database;
    }

    public void setDriver(String driver) {
        if(driver == null || driver.equals(""))
            throw new NullPointerException("[ driver ] cannot be empty or null => " + this.getClass().getName());
        else
            this.driver = driver;
    }

    public void setSchema(String schema) {
        if(schema == null || schema.equals(""))
            throw new NullPointerException("[ schema ] cannot be empty or null => " + this.getClass().getName());
        else
            this.schema = schema;
    }

    public void setCharset(String charset) {
        if(charset == null || charset.equals(""))
            throw new IllegalArgumentException("[ charset ] cannot be empty or null => " + this.getClass().getName());
        else
            this.charset = charset;
    }
    
}
