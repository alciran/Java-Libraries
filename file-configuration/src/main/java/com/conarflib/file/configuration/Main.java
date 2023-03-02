package com.conarflib.file.configuration;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ConfigurationFileIni fileIni = new ConfigurationFileIni("C://Medcloud//config.ini");

            fileIni.setLoadExceptionWhenPropertyValueIsNull(true);

            System.out.println(fileIni.getProperty("PAT", "databases"));

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }
}
