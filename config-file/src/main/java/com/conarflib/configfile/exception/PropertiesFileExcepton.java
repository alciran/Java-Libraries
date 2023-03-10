package com.conarflib.configfile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Exception class thrown when an error occurs while using the configuration
 * file implementation.
 * 
 * @author Alciran Franco
 */
@Getter
@Setter
@AllArgsConstructor
public class PropertiesFileExcepton extends RuntimeException {
    private String message;
}
