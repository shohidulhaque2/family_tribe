package com.shohidulhaque.api_rest_service.endpoints;


import com.shohidulhaque.my_people.common_model.data_transfer_object.storage_service.ImageDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.storage_service.ImageDtoResponse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/v1")
public class UserStorageEndPoints {

    WebClient webClient;

    @Autowired
    public UserStorageEndPoints(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/media/{uuidOfUser}/avatar")
    public ResponseEntity<ImageDtoResponse> storeAvatar(@PathVariable("uuidOfUser") UUID uuidOfUser, @RequestBody ImageDtoRequest imageDtoRequest){
       return ResponseEntity.ok().build();
    }

    @PostMapping("/media/{uuidOfUser}/avatar")
    public ResponseEntity<ImageDtoResponse> getAvatar(@PathVariable("uuidOfUser") UUID uuidOfUser){
        return ResponseEntity.ok().build();
    }

}
