package com.shohidulhaque.my_people.discovery_service.config;

import java.util.Observer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestExperiment {

  @Test
  public void simpleTest() {
    Observer observer = Mockito.mock(Observer.class);
    Mockito.doNothing().when(observer).update(Mockito.any(), Mockito.any());
    observer.update(null, new Object());
    Mockito.verify(observer).update(Mockito.any(), Mockito.any());
  }

}
