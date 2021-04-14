package com.shohidulhaque.my_people.common_utility;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtility {
    public static <T> ResponseEntity <T> RemoveChunkedHeader(ResponseEntity<T> responseEntity){
        HttpHeaders httpHeaders = new HttpHeaders();
        responseEntity.getHeaders().forEach(httpHeaders::put);
        //output is not chunked, but is reported as such. causes axios to break. what a pain. should not be this hard.
        httpHeaders.remove("Transfer-Encoding");
        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .headers(httpHeaders)
                .body(responseEntity.getBody());
    }
}
