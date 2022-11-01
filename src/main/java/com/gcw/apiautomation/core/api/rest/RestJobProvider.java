package com.gcw.apiautomation.core.api.rest;

import com.gcw.apiautomation.core.api.ApiJobHelper;
import com.gcw.apiautomation.core.api.restimpl.RestCreateJob;
import com.gcw.apiautomation.core.api.restimpl.RestReadJob;


public class RestJobProvider extends ApiJobHelper {
    //POST
    public void postPayload(){
        this.setRestJob(new RestCreateJob());
        this.getRestJob().perform(this.getEntity());
    }

    //GET
    public void getResource(){
        this.setRestJob(new RestReadJob());
        this.getRestJob().perform(this.getEntity());
    }



    public String getResponseJson(){
        return this.getEntity().getResponsePayload();
    }

    public int getResourceCode(){
        return this.getEntity().getResponseCode();
    }

    public String getRequestBody(){
        return this.getEntity().getRequestPayload();
    }

    public void setRequestBody(final String requestBody){
        this.getEntity().setResponsePayload(requestBody);
    }

    public void setEndPoint(final String endpoint){
        if (endpoint.startsWith("/")){
            this.getEntity().setEndPoint(endpoint);
        }else {
            this.getEntity().setEndPoint("/"+endpoint);
        }
    }

    public void setBaseUri(String uri){
        this.getEntity().setBaseUri(uri);
    }

    public void setBasePath(String path){
        this.getEntity().setBasePath(path);
    }





}
