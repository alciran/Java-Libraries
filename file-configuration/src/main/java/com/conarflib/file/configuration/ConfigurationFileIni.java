package com.conarflib.file.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.ini4j.Ini;

public class ConfigurationFileIni extends PropertiesFile {

    private String filePath = "config.ini";
    private Ini ini;

    public ConfigurationFileIni(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty())
            throw new IllegalArgumentException("Attribute [ filePath ] must not be null or empty!");

        this.filePath = filePath;
        this.ini = new Ini(new File(this.filePath));
    }

    public String getProperty(String sectionName, String propertyName) {
        return this.checkAndReturnPropertyValue(propertyName,
                ini.get(sectionName).get(propertyName));
    }

    public Map<String, String> getPropertiesMap(String[] properties) {
        return null;
    }

}
