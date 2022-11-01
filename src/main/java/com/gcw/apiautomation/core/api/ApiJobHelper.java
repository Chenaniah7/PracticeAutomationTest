package com.gcw.apiautomation.core.api;

import com.gcw.apiautomation.core.domain.entity.Entity;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ApiJobHelper extends ApiJob {
    private  static final Logger LOGGER = LoggerFactory.getLogger(ApiJobHelper.class.getName());


    public void clearHeaders(){
        Map<String,Object> emptyRequestHeaders =  new HashMap<>();
        this.getEntity().setRequestHeaders(emptyRequestHeaders);
    }

    public void removeHeaders(final List<String> headersNames){
        final Entity entity = this.getEntity();
        final Iterator<String> iterator = headersNames.iterator();
        final Map<String,Object> requestHeaderMap = entity.getRequestHeaders();

        while (iterator.hasNext()){
            final String headerName = iterator.next();
            requestHeaderMap.remove(headerName);
        }
        entity.setRequestHeaders(requestHeaderMap);
    }

    public void removeHeader(final String headerName){
        this.getEntity().getRequestHeaders().remove(headerName);
    }


    public void updateHeader(final String header, final String value){
        this.getEntity().getRequestHeaders().put(header,value);
    }

    public void updateHeaders(final Map<String,Object> headers){
        this.getEntity().getRequestHeaders().putAll(headers);
    }

    public void clearQueryParams(){
        final Entity entity = this.getEntity();
        final Map<String,Object> emptyQueryParams =  new HashMap<>();
        entity.setQueryParams(emptyQueryParams);
    }

    public void updateQueryParam(String key, Object value){
        this.getEntity().getQueryParams().put(key, value);
    }


    public void updateQueryParams(Map<String,Object> queryParams){
        this.getEntity().getQueryParams().putAll(queryParams);
    }

    public void updatePathParam(String key, Object value){
        this.getEntity().getPathParams().put(key, value);
    }


    public void removePathParam(final String pathParam){
        this.getEntity().getPathParams().remove(pathParam);
    }

    public void cleanPathParams(){
        final Map<String,Object> pathParams = new HashMap<>();
        this.getEntity().setPathParams(pathParams);
    }


    public void loadPayload(String filePath){
        String payloadPath = "/payload"+ File.separator +filePath;
        InputStream inputStream = this.getClass().getResourceAsStream(payloadPath);
        String content = "";
        if (inputStream != null){
            try {
                 content = IOUtils.toString(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.getEntity().setRequestPayload(content);
    }









}
