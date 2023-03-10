package com.conarflib.file.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.ini4j.Ini;

public class IniConfigurationFile extends PropertiesFile {

    private Ini ini;    

    public IniConfigurationFile(File configFile) throws IOException{
        super(configFile);
        this.ini = new Ini(getConfigFile());
    } 
    
    public IniConfigurationFile(File configFile, boolean loadExceptionWhenPropertyValueIsNull) throws IOException{
        super(configFile);
        setLoadExceptionWhenPropertyValueIsNull(loadExceptionWhenPropertyValueIsNull);
        this.ini = new Ini(getConfigFile());
    } 

    private void checkSectionName(String sectionName){
        if(sectionName == null || sectionName.isEmpty())
            throw new NullPointerException("Attribute [ sectionName ] must not be null or empty!");
    }

    private void checkPropertyValue(String propertyName, String propertyValue){
        if(this.getLoadExceptionWhenPropertyValueIsNull() && propertyValue == null)
            throw new NullPointerException("Property [ " + propertyName + " ] not found in file " + getConfigFile().getPath());
    }

    public String getProperty(String sectionName, String propertyName){  
        return getEncryptedProperty(sectionName, propertyName, null);
    }

    public String getProperty(String sectionName, String propertyName, String defaultValue){  
        checkSectionName(sectionName);
        checkPropertyName(propertyName);
        String propertyValue = ini.get(sectionName).get(propertyName);         
        checkPropertyValue(propertyName, propertyValue);
        return propertyValue == null ? defaultValue : propertyName;
    }

    public String getEncryptedProperty(String sectionName, String propertyName){
        String propertyValue = getProperty(sectionName, propertyName);
        return symmetricDecrypt(propertyValue);
    }

    public String getEncryptedProperty(String sectionName, String propertyName, String secretKey){
        String propertyValue = getProperty(sectionName, propertyName);
        return symmetricDecrypt(propertyValue, secretKey);
    }

    public Map<String,String> getSectionProperties(String sectionName){
        checkSectionName(sectionName);
        Map<String,String> mapProperties = ini.get(sectionName);
        for (Map.Entry<String, String> entry : mapProperties.entrySet()) {
            checkPropertyValue(entry.getKey(), entry.getValue());
        }
        return mapProperties;
    }   

    private Map<String,String> getAllEncryptedSection(String sectionName, String secretKey){
        Map<String,String> mapProperties = new HashMap<>();
        getSectionProperties(sectionName).forEach((key, value) -> mapProperties.put(key, symmetricDecrypt(value, secretKey)));
        return mapProperties;            
    }    

    private Map<String,String> getEncryptedSectionByList(String sectionName, String secretKey, String[] encryptedProperties){
        if(encryptedProperties == null)
            return getAllEncryptedSection(sectionName, secretKey);
        else{
            Map<String,String> mapProperties = new HashMap<>();
            getSectionProperties(sectionName).forEach((key, value) -> {
                if(Arrays.stream(encryptedProperties).anyMatch(key::contains))
                    mapProperties.put(key, symmetricDecrypt(value, secretKey));
                else
                    mapProperties.put(key,value);
            });
            return mapProperties;
        }           
    }

    public Map<String,String> getEncryptedSectionProperties(String sectionName){        
        return getAllEncryptedSection(sectionName, null);
    }    

    public Map<String,String> getEncryptedSectionPropertiesBySecretKey(String sectionName, String secretKey){
        return getAllEncryptedSection(sectionName, secretKey);
    }

    public Map<String,String> getEncryptedSectionProperties(String sectionName, String... encryptedProperties){
        return getEncryptedSectionByList(sectionName, null, encryptedProperties);
    }

    public Map<String,String> getEncryptedSectionPropertiesBySecretKey(String sectionName, String secretKey, String... encryptedProperties){
        return getEncryptedSectionByList(sectionName, secretKey, encryptedProperties);        
    }

}
