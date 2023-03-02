package com.conarflib.file.configuration;

public abstract class PropertiesFile {

    protected boolean loadExceptionWhenPropertyValueIsNull = false;
    private static String ENVIRONMENT_VAR = "CONFIGURATION_FILE_KEY";

    public PropertiesFile() {
    }

    public void setLoadExceptionWhenPropertyValueIsNull(boolean loadExceptionWhenPropertyValueIsNull) {
        this.loadExceptionWhenPropertyValueIsNull = loadExceptionWhenPropertyValueIsNull;
    }

    protected String checkAndReturnPropertyValue(String propertyName, String propertyValue) {
        if (this.loadExceptionWhenPropertyValueIsNull && propertyValue == null)
            throw new NullPointerException(
                    "Property [ " + propertyName + " ] has null value or not exists in the file!");
        else
            return propertyValue;
    }

}
