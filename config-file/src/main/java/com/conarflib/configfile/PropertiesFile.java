package com.conarflib.configfile;

import java.io.File;

import com.conarflib.configfile.exception.PropertiesFileExcepton;
import com.conarflib.security.SymmetricEncryption;

public abstract class PropertiesFile {

    private boolean loadExceptionWhenPropertyValueIsNull = false;
    private static String ENVIRONMENT_VAR = "CONFIGURATION_FILE_KEY";
    private File configFile;

    public PropertiesFile(File configFile) {
        this.setConfigFile(configFile);
    }

    private void setConfigFile(File configFile){
        if(configFile == null)        
            throw new NullPointerException("Attribute [ configFile ] must not be null!"); 
        this.configFile = configFile;
    }

    protected File getConfigFile() {
        return this.configFile;
    }

    protected void setLoadExceptionWhenPropertyValueIsNull(boolean loadExceptionWhenPropertyValueIsNull) {
        this.loadExceptionWhenPropertyValueIsNull = loadExceptionWhenPropertyValueIsNull;
    }

    protected boolean getLoadExceptionWhenPropertyValueIsNull() {
        return this.loadExceptionWhenPropertyValueIsNull;
    }

    protected void checkPropertyName(String propertyName){
        if(propertyName == null || propertyName.isEmpty())
            throw new NullPointerException("Attribute [ propertyName ] must not be null or empty!");
    }

    protected static String symmetricEncrypt(String value) {
        return symmetricEncrypt(value, getEnvironmentVariableValue());
    }

    protected static String symmetricEncrypt(String value, String secretKey) {
        try {
            return SymmetricEncryption.encrypt(value, SymmetricEncryption.getKey(secretKey));
        } catch (Exception exCrypt) {
            throw new PropertiesFileExcepton("Error on encrypt: " + exCrypt.getMessage());
        }
    }

    protected static String symmetricDecrypt(String value) {
        return symmetricDecrypt(value, null);
    }

    protected static String symmetricDecrypt(String value, String secretKey) {
        try {
            if (secretKey == null)
                secretKey = getEnvironmentVariableValue();
            return SymmetricEncryption.decrypt(value, SymmetricEncryption.getKey(secretKey));
        } catch (Exception exDecrypt) {
            throw new PropertiesFileExcepton("Error on decrypt: " + exDecrypt.getMessage());
        }
    }

    private static String getEnvironmentVariableValue() {
        String enviromentVariableValue = System.getenv(ENVIRONMENT_VAR);
        if (enviromentVariableValue == null || enviromentVariableValue.isEmpty())
            throw new NullPointerException(
                    "Environment variable [ " + ENVIRONMENT_VAR + " ] not found or null/empty value!");
        return enviromentVariableValue;
    }

}
