package com.gcw.apiautomation.core;

import com.gcw.apiautomation.core.api.rest.RestJobProvider;

import java.util.HashMap;
import java.util.Map;

public class BaseStep extends RestJobProvider {


    public Map<String,Object> testValue = new HashMap<>();

    public Map<String, Object> getTestValue() {
        return testValue;
    }

    public void setTestValue(Map<String, Object> testValue) {
        this.testValue = testValue;
    }
}
