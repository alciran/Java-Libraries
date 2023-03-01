package com.conarflib.file.configuration;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

public abstract class PropertiesFile {
    protected String filePath;
    protected boolean loadExceptionWhenPropertyValueIsNull = false;
    protected File file;

    public void list() {

        File file = new File("C://Medcloud//config.ini");

        try {
            Ini ini = new Ini(file);

            Preferences prefs = new IniPreferences(ini);

            System.out.println("Node: [PAT] : " + prefs.node("PAT").get("database", null));
        } catch (IOException ioEx) {
            throw new RuntimeException("Error open file [" + filePath + "] => " + ioEx.getMessage());
        }
    }

}
