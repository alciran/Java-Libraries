package com.conarflib.file.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ini4j.Ini;
import com.conarflib.file.configuration.exception.PropertiesFileExcepton;


public class ConfigurationFileIni extends PropertiesFile {

    private Ini ini;    

    public ConfigurationFileIni(File configFile) throws IOException{
        super(configFile);
        this.ini = new Ini(getConfigFile());
    }   

    private void checkSectionName(String sectionName){
        if(sectionName == null || sectionName.isEmpty())
            throw new IllegalArgumentException("Attribute [ sectionName ] must not be null or empty!");
    }

    private void checkPropertyName(String propertyName){
        if(propertyName == null || propertyName.isEmpty())
            throw new IllegalArgumentException("Attribute [ propertyName ] must not be null or empty!");
    }

    private void checkPropertyValue(String propertyName, String propertyValue){
        if(this.getLoadExceptionWhenPropertyValueIsNull() && propertyValue == null)
            throw new PropertiesFileExcepton("Property [ " + propertyName + " ] not found in file " + getConfigFile().getPath());
    }

    public String getProperty(String sectionName, String propertyName){  
        checkSectionName(sectionName);
        checkPropertyName(propertyName);
        String propertyValue = ini.get(sectionName).get(propertyName);         
        checkPropertyValue(propertyName, propertyValue);
        return propertyValue;
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
        Map<String,String> map = ini.get(sectionName);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            checkPropertyValue(entry.getKey(), entry.getValue());
        }
        return map;
    }   

    private Map<String,String> getAllEncryptedSection(String sectionName, String secretKey){
        Map<String,String> map = new HashMap<>();
        getSectionProperties(sectionName).forEach((key, value) -> map.put(key, symmetricDecrypt(value, secretKey)));
        return map;            
    }    

    private Map<String,String> getEncryptedSectionByList(String sectionName, String secretKey, String[] encryptedProperties){
        if(encryptedProperties == null)
            return getAllEncryptedSection(sectionName, secretKey);
        else{
            List<String> listEncryptedProperties = Arrays.asList(encryptedProperties);
            Map<String,String> map = new HashMap<>();
            getSectionProperties(sectionName).forEach((key, value) -> {
                if(listEncryptedProperties.contains(key))
                    map.put(key, symmetricDecrypt(value, secretKey));
                else
                    map.put(key,value);
            });
            return map;
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

    public Map<String,String> getEncryptedSectionPropertiesByScretKey(String sectionName, String secretKey, String... encryptedProperties){
        return getEncryptedSectionByList(sectionName, secretKey, encryptedProperties);        
    }

}
