package com.shohidulhaque.api_rest_service;

import lombok.Data;

@Data
public class TestObject {
  TestObjectClient testObjectClient;
  boolean hasUpdated;
  public void updateSelf(){
    if(testObjectClient.hasChanged()){
      this.hasUpdated = true;
    }
  }



  public void callMethodWithArgument(String d){
    this.testObjectClient.justAnArgument(d);
  }
}
