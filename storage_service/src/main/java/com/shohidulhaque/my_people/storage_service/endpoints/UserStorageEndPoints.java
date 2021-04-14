package com.shohidulhaque.my_people.storage_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.storage_service.ImageDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.storage_service.ImageDtoResponse;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user-storage-service")
@RestController
public class UserStorageEndPoints {

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/strorage/{user}")
    public ResponseEntity<Void> createChatSpace(@PathVariable("user") String userId, @RequestBody Object chatSpaceRequestDto){
        return null;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/storage/{user}")
    public ResponseEntity<Void> getChatSpace(@PathVariable("user") String userId){
        return null;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/media/{uuidOfUser}/avatar")
    public ResponseEntity<ImageDtoResponse> storeAvatar(@PathVariable("uuidOfUser") UUID uuidOfUser, @RequestBody ImageDtoRequest imageDtoRequest){
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/media/{uuidOfUser}/avatar")
    public ResponseEntity<ImageDtoResponse> getAvatar(@PathVariable("uuidOfUser") UUID uuidOfUser){
        return ResponseEntity.ok().build();
    }

}
