package com.shohidulhaque.my_people.common_model;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BasicTest {

  TestObject testObject;
  @Mock
  TestObjectClient observable;

  @BeforeEach
  public void setUpTestObjects() {
    testObject = new TestObject();
//    observable = Mockito.mock(Observable.class);
    testObject.setTestObjectClient(observable);
  }

  @Test
  @DisplayName("Given an observer has changed when an event has then update self.")
  public void simpleTest() {
    Mockito.when(observable.hasChanged()).thenReturn(true);
    testObject.updateSelf();
    Mockito.verify(observable, Mockito.times(1)).hasChanged();
  }

  @Test
  public void simpleTest2() {
    Mockito.doNothing().when(observable).justAnArgument("1");
    testObject.callMethodWithArgument("1");
    Mockito.verify(observable).justAnArgument("1");
  }

  @ParameterizedTest
  @CsvSource({"1",
      "1",
      "1",
      "1",
      "1"})
  public void parametricTest(int a) {
    Assert.assertEquals(1, a);
  }


}
