package com.conarflib.file.configuration;

public abstract class PropertiesFile {

    protected boolean loadExceptionWhenPropertyValueIsNull = false;
    protected SymmetricCryptService cryptService;

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
