package com.shohidulhaque.my_people.discovery_service.rest_end_points;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping(value = "/")
  public String index() {
    return "index";
  }

}