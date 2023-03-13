package com.conarflib.web.apirest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.conarflib.http.HttpMethod;
import com.conarflib.util.ObjectUtils;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRestClientRequest {

    private HttpMethod requestHttpMethod;
    private Map<String, String> requestHeaders;
    private String charset = "UTF-8";
    private int timeout = 30000; //30s
    private URL url;

    public ApiRestClientRequest(URL url, HttpMethod httpMethod){
        ObjectUtils.requiredNonNull(url, "Attribute [ url ] must not be null!");
        ObjectUtils.requiredNonNull(httpMethod, "Attribute [ httpMethod ] must not be null!");
        this.url = url;
        this.requestHttpMethod = httpMethod;
        this.setDefaultRequestHeaders();
    }

    private void setDefaultRequestHeaders(){
        this.requestHeaders = new HashMap<>();
        this.requestHeaders.put("Content-Type", "application/json");
        this.requestHeaders.put("accept", "application/json");
    }

    public void addRequestHeader(String key, String value){
        if(this.requestHeaders == null)
            this.requestHeaders = new HashMap<>();
        ObjectUtils.requiredNonNull(key, "Key of header must not be null!");
        this.requestHeaders.put(key, value);
    }

    private HttpURLConnection prepareConnection() throws IOException{
        HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();

        //set method
        connection.setRequestMethod(this.requestHttpMethod.name());
        connection.setDoOutput(true);
        connection.setDoInput(true);

        //Set Headers
        this.requestHeaders.forEach((key,value) -> connection.setRequestProperty(key, value));

        return connection;
    }

    public ApiRestClientResponse execute() throws IOException{
        return executeRequest(null);
    }

    public ApiRestClientResponse executeRequest(Object requestBody) throws IOException {
        HttpURLConnection connection = prepareConnection();

        //set Object Request
        if(requestBody != null){
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(new Gson().toJson(requestBody));
            writer.close();
        }                
        //close connection
        connection.disconnect();

        return generateResponse(connection);
    }

    private ApiRestClientResponse generateResponse(HttpURLConnection connection) throws IOException{
        ApiRestClientResponse response = new ApiRestClientResponse();

        response.setHttpStatusCode(connection.getResponseCode());
        response.setHttpStatusMessage(connection.getResponseMessage());

        InputStream stream = connection.getErrorStream();

        if (stream == null) {
            InputStreamReader in = new InputStreamReader(connection.getInputStream(), charset);
            BufferedReader br = new BufferedReader(in);

            Object object = br.readLine();
            response.setResponseBodyObject(object);

        }else{
            try (Scanner scanner = new Scanner(stream)) {
                scanner.useDelimiter("\\Z");
                Object object = scanner.next();
                response.setResponseBodyObject(object);
            }
        }

        return response;
    }

}
