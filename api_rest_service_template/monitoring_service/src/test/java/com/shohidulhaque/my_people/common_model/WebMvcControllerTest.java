package com.shohidulhaque.my_people.common_model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//https://spring.io/guides/gs/testing-web/

//@ContextConfiguration()
public class WebMvcControllerTest {

  @Autowired
  private MockMvc mockMvc;

  //RequestBuilders
  //ResultHandlers
  //ResultMatchers
  //RequestPostProcessors

  //MockMvcRequestBuilders
  //SecurityMockMvcRequestPostProcessors
  //MockMvcResultMatchers
  @Test
  @DisplayName("Simple Null Point Test")
  public void simpleNullPointTest() throws Exception {

//    this.mockMvc
//        .perform(MockMvcRequestBuilders.get("/null").with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password")))
//        .andDo(MockMvcResultHandlers.print())
//        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
