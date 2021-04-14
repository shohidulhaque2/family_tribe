package com.shohidulhaque.my_people.common_model.http;

public enum ContentType {
    Application_Hal_Json("application/hal+json");
    ContentType(String  contentType){
        this.ContentType = contentType;
    }
    public final String ContentType;
}