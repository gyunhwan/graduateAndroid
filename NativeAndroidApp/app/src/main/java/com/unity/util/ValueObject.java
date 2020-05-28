package com.unity.util;

public class ValueObject {
    private String key;
    private String type;
    public ValueObject(String key,String type){
        this.key=key;
        this.type=type;
    }
    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(String type) {
        this.type = type;
    }
}
