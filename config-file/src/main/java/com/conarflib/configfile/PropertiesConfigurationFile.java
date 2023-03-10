package com.conarflib.configfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

<<<<<<< HEAD:file-configuration/src/main/java/com/conarflib/file/configuration/PropertiesConfigurationFile.java
=======
import com.conarflib.configfile.exception.PropertiesFileExcepton;

>>>>>>> b3937a6 (Rename module from file-configuration to config-file, and adjusted the path class name):config-file/src/main/java/com/conarflib/configfile/PropertiesConfigurationFile.java
public class PropertiesConfigurationFile extends PropertiesFile {

    private static Properties properties = new Properties();

    public PropertiesConfigurationFile(File configFile) throws IOException {
        super(configFile);
        setProperties();
    }

    public PropertiesConfigurationFile(File configFile, boolean loadExceptionWhenPropertyValueIsNull)
            throws IOException {
        super(configFile);
        setLoadExceptionWhenPropertyValueIsNull(loadExceptionWhenPropertyValueIsNull);
        setProperties();
    }

    private void setProperties() throws IOException {
        FileInputStream streamConfigFile = new FileInputStream(getConfigFile());
        properties.load(streamConfigFile);
    }

<<<<<<< HEAD:file-configuration/src/main/java/com/conarflib/file/configuration/PropertiesConfigurationFile.java
    private void checkPropertyValue(String propertyName, String propertyValue){
        if(this.getLoadExceptionWhenPropertyValueIsNull() && propertyValue == null)
            throw new NullPointerException("Property [ " + propertyName + " ] not found in file " + getConfigFile().getPath());
=======
    private void checkPropertyValue(String propertyName, String propertyValue) {
        if (this.getLoadExceptionWhenPropertyValueIsNull() && propertyValue == null)
            throw new PropertiesFileExcepton(
                    "Property [ " + propertyName + " ] not found in file " + getConfigFile().getPath());
>>>>>>> b3937a6 (Rename module from file-configuration to config-file, and adjusted the path class name):config-file/src/main/java/com/conarflib/configfile/PropertiesConfigurationFile.java
    }

    public String getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }

    public String getProperty(String propertyName, String defaultValue) {
        checkPropertyName(propertyName);
        String propertyValue = properties.getProperty(propertyName, defaultValue);
        checkPropertyValue(propertyName, propertyValue);
        return propertyValue == null ? defaultValue : propertyValue;
    }

    public String getEncryptedProperty(String propertyName) {
        String propertyValue = getProperty(propertyName);
        return symmetricDecrypt(propertyValue);
    }

    public String getEncryptedProperty(String propertyName, String secretKey) {
        String propertyValue = getProperty(propertyName);
        return symmetricDecrypt(propertyValue, secretKey);
    }

<<<<<<< HEAD:file-configuration/src/main/java/com/conarflib/file/configuration/PropertiesConfigurationFile.java
    public Map<String, String> getProperties(String... properties){
        if(properties == null)
            throw new NullPointerException("Attribute [ properties ] must not be null");
        
=======
    public Map<String, String> getProperties(String... properties) {
        if (properties == null)
            throw new PropertiesFileExcepton("Attribute [ properties ] must not be null");

>>>>>>> b3937a6 (Rename module from file-configuration to config-file, and adjusted the path class name):config-file/src/main/java/com/conarflib/configfile/PropertiesConfigurationFile.java
        Map<String, String> mapProperties = new HashMap<>();
        Arrays.stream(properties).forEach(property -> mapProperties.put(property, getProperty(property)));

        return mapProperties;
    }

    public Map<String, String> getEncryptedProperties(String... properties) {
        Map<String, String> mapProperties = new HashMap<>();
        getProperties(properties).forEach((key, value) -> mapProperties.put(key, symmetricDecrypt(value)));
        return mapProperties;
    }

    public Map<String, String> getEncryptedProperties(String secretKey, String... encryptedProperties) {
        Map<String, String> mapProperties = new HashMap<>();
        getProperties(encryptedProperties).forEach((key, value) -> {
            if (Arrays.stream(encryptedProperties).anyMatch(key::contains))
                mapProperties.put(key, symmetricDecrypt(value, secretKey));
            else
                mapProperties.put(key, value);
        });
        return mapProperties;
    }

}
