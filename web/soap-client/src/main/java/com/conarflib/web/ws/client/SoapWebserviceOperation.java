package com.conarflib.web.ws.client;

public interface SoapWebserviceOperation {

    String getOperationName();

    Object[] getOperationBodyRequest();
}
