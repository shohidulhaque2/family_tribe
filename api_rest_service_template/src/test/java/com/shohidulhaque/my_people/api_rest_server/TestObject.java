package com.shohidulhaque.my_people.common_model;

import java.util.Observable;
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
