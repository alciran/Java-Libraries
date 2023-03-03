package com.conarflib.file.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertiesFileExcepton extends RuntimeException {
    private String message;
}
