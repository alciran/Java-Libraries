package com.conarflib.file.configuration;

import com.conarflib.file.configuration.exception.PropertiesFileExcepton;
import com.conarflib.security.SymmetricEncryption;

public class SymmetricCryptService {

    private static String ENVIRONMENT_VAR = "CONFIGURATION_FILE_KEY";

    private static String getEnvironmentVariableValue() {
        String enviromentVariableValue = System.getenv(ENVIRONMENT_VAR);
        if (enviromentVariableValue == null || enviromentVariableValue.isEmpty())
            throw new PropertiesFileExcepton(
                    "Environment variable [ " + ENVIRONMENT_VAR + " ] not found or null/empty value!");
        return enviromentVariableValue;
    }

    public static String encrypt(String value) {
        return encrypt(value, getEnvironmentVariableValue());
    }

    public static String encrypt(String value, String secretKey) {
        try {
            return SymmetricEncryption.encrypt(value, SymmetricEncryption.getKey(secretKey));
        } catch (Exception exCrypt) {
            throw new PropertiesFileExcepton("Error on encrypt: " + exCrypt.getMessage());
        }
    }

    public static String decrypt(String value) {
        return decrypt(value, getEnvironmentVariableValue());
    }

    public static String decrypt(String value, String secretKey) {
        try {
            return SymmetricEncryption.decrypt(value, SymmetricEncryption.getKey(secretKey));
        } catch (Exception exDecrypt) {
            throw new PropertiesFileExcepton("Error on decrypt: " + exDecrypt.getMessage());
        }
    }

}
