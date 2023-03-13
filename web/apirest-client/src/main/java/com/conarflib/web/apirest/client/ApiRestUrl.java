package com.conarflib.web.apirest.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.conarflib.util.ObjectUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiRestUrl {
    
    private Map<String, String> queryStringParamaters;
    private String host;
    private String resource;
    private String pathParameters;

    public ApiRestUrl(String host){
        ObjectUtils.requiredNonNull(host, "Attribute [ host ] must not be null!");        
        this.host = host;
    }

    public ApiRestUrl(String host, String resource, String pathParameters){
        ObjectUtils.requiredNonNull(host, "Attribute [ host ] must not be null!");  
        ObjectUtils.requiredNonNull(resource, "Attribute [ resource ] must not be null!"); 

        this.host = host;
        this.resource = resource;
        this.pathParameters = pathParameters;
    }

    public void addQueryStringParameter(String key, String value){
        if(this.queryStringParamaters == null)
            this.queryStringParamaters = new HashMap<>();
        this.queryStringParamaters.put(key, value);
    }

    public String getUrl(){
        String url = host;

        if (pathParameters == null)
            url = host + "/" + resource;
        else
            url = host + "/" + resource + pathParameters;

        if (this.queryStringParamaters != null) {
            if (!this.queryStringParamaters.isEmpty()) {
                String mapQueryParameters = "?";
                Iterator<Map.Entry<String, String>> it = this.queryStringParamaters.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    mapQueryParameters += entry.getKey() + "=" + entry.getValue();
                    if (it.hasNext())
                        mapQueryParameters += "&";
                }
                url += mapQueryParameters;
            }
        }

        return url;
    }

    public URL getURL() throws MalformedURLException {
        return new URL(this.getUrl());
    }
}
