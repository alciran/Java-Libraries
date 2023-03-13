package com.conarflib.web.apirest.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiRestClientResponse {

    private int httpStatusCode;
    private String httpStatusMessage;
    private Object responseBodyObject;

    public <T> T getByResponseBodyObjectJson(Class<T> type) throws JsonProcessingException{
        if(getResponseBodyObject() != null){
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(this.getResponseBodyObject().toString(), type);
        }else
            return null;        
    }
    
}
