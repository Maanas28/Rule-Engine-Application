package com.example.ruleengine.ProjectApplication1.model;

import java.util.Map;

public class RequestData {
    private String astId;
    private Map<String, Object> data;


    public String getAstId() {
        return astId;
    }

    public void setAstId(String astId) {
        this.astId = astId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
