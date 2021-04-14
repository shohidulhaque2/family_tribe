package com.shohidulhaque.my_people.discovery_service.rest_end_points;

import com.shohidulhaque.my_people.discovery_service.data_transfer_object.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NullEndPoint {

  @RequestMapping(value = RestEndPointPaths.NullEndPoint, method = RequestMethod.GET)
  public ResponseEntity<Message> nullEndPoint(Authentication auth) {
    return ResponseEntity.ok(new Message("null end point"));
  }

}
