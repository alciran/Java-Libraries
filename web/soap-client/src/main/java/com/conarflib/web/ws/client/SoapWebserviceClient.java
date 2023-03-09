package com.conarflib.web.ws.client;

import java.rmi.RemoteException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import javax.xml.rpc.ServiceException;

public class SoapWebserviceClient {

    private static String defaultEncodingStyle = "ISO-8859-1"; // default encoding style.

    public static Object executeRequest(String soapWebServerAddress, String operation)
            throws ServiceException, RemoteException, NullPointerException {
        return call(soapWebServerAddress, operation, defaultEncodingStyle, null);
    }

    public static Object executeRequest(String soapWebServerAddress, String operation, String encodingStyle)
            throws ServiceException, RemoteException, NullPointerException {
        return call(soapWebServerAddress, operation, encodingStyle, null);
    }

    public static Object executeRequest(String soapWebServerAddress, String operation, String encodingStyle,
            Object[] bodyRequest)
            throws ServiceException, RemoteException, NullPointerException {
        return call(soapWebServerAddress, operation, encodingStyle, bodyRequest);
    }

    public static Object executeRequest(String soapWebServerAddress, SoapWebserviceOperation operation)
            throws ServiceException, RemoteException, NullPointerException {
        return call(soapWebServerAddress, operation.getOperationName(), defaultEncodingStyle,
                operation.getOperationBodyRequest());
    }

    public static Object executeRequest(String soapWebServerAddress, SoapWebserviceOperation operation,
            String encodingStyle) throws ServiceException, RemoteException, NullPointerException {
        return call(soapWebServerAddress, operation.getOperationName(), encodingStyle,
                operation.getOperationBodyRequest());
    }

    private static Object call(String soapWebServerAddress, String operation, String encodingStyle,
            Object[] bodyRequest) throws ServiceException, RemoteException, NullPointerException {
        Call call = (Call) new Service().createCall();
        call.setTargetEndpointAddress(soapWebServerAddress);
        call.setEncodingStyle(encodingStyle);

        // Set the operationName
        call.setOperationName(operation);

        return call.invoke(bodyRequest);
    }

}
