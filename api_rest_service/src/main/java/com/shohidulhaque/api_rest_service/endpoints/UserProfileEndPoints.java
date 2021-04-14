package com.shohidulhaque.api_rest_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoResponse;
import com.shohidulhaque.my_people.common_utility.ResponseEntityUtility;
import com.shohidulhaque.my_people.common_utility.UuidGrantedAuthority;
import java.util.Map;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/user-profile-service")
@RestController
public class UserProfileEndPoints {

    static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserProfileEndPoints.class);

    WebClient webClient;

    public UserProfileEndPoints(WebClient webClient) {
        this.webClient = webClient;
    }

    private Optional<String> getUserUuid(OAuth2AuthenticationToken token) {
        for (GrantedAuthority ga : token.getAuthorities()) {
            if (ga instanceof UuidGrantedAuthority) {
                return Optional.of(ga.getAuthority());
            }
        }
        return Optional.empty();
    }

    //==============================================================================================
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/users/current_user")
//    public ResponseEntity<UserProfileDtoResponse> createUser(OAuth2AuthenticationToken token) {
////        Optional<String> uuidOptional = getUserUuid(token);
////        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
////            .get()
////            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
////            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
////            .doOnError(x -> {
////                Logger.warn("error", x);
////            })
////            .block();
//        return ResponseEntity.ok().build();
//    }

//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping("/users/current_user")
//    public ResponseEntity<UserProfileDtoResponse> deleteUser(OAuth2AuthenticationToken token) {
////        Optional<String> uuidOptional = getUserUuid(token);
////        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
////            .get()
////            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
////            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
////            .doOnError(x -> {
////                Logger.warn("error", x);
////            })
////            .block();
//        return ResponseEntity.ok().build();
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/users/current_user")
    public ResponseEntity<UserProfileDtoResponse> createUserProfile(
        OAuth2AuthenticationToken token,
        @PathVariable("profileId") String profileId) {
//        Optional<String> uuidOptional = getUserUuid(token);
//        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
//            .get()
//            .uri("lb://USER-userProfileEndPointsPROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
//            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
//            .doOnError(x -> {
//                Logger.warn("error", x);
//            })
//            .block();
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/users/current_user")
    public ResponseEntity<UserProfileDtoResponse> deleteUserProfile(
        OAuth2AuthenticationToken token,
        @PathVariable("profileId") String profileId) {
//        Optional<String> uuidOptional = getUserUuid(token);
//        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
//            .get()
//            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
//            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
//            .doOnError(x -> {
//                Logger.warn("error", x);
//            })
//            .block();
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/current_user")
    public ResponseEntity<UserProfileDtoResponse> getUserProfile(
        OAuth2AuthenticationToken token) {
        Optional<String> uuidOptional = getUserUuid(token);
        ResponseEntity<UserProfileDtoResponse> responseEntity = this.webClient
            .get()
            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/users/{profileId}", Map.of("profileId", uuidOptional.get()))
            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
            .doOnError(x -> {
                Logger.warn("error", x);
            })
            .block();
        return ResponseEntityUtility.RemoveChunkedHeader(responseEntity);
    }

//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/users/current_user")
//    public ResponseEntity<UserProfileDtoResponse> getUserProfiles(
//        OAuth2AuthenticationToken token) {
////        Optional<String> uuidOptional = getUserUuid(token);
////        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
////            .get()
////            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
////            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
////            .doOnError(x -> {
////                Logger.warn("error", x);
////            })
////            .block();
//        return ResponseEntity.ok().build();
//    }


    @PreAuthorize("isAuthenticated()")
    @PutMapping("/users/current_user")
    public ResponseEntity<UserProfileDtoResponse> updateUserProfile(
        OAuth2AuthenticationToken token,
        @RequestBody UserProfileDtoRequest userProfileDtoRequest) {
        Optional<String> uuidOptional = getUserUuid(token);
        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
            .put()
            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/users/{profileId}",
                Map.of("profileId", uuidOptional.get()))
            .body(Mono.just(userProfileDtoRequest), UserProfileDtoRequest.class)
            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
            .doOnError(x -> {
                Logger.warn("error", x);
            })
            .block();
        return ResponseEntityUtility.RemoveChunkedHeader(userProfileDtoResponse);
    }
    //==============================================================================================

    // /api/v1/user-profile-service"
    //
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/current_user")
//    public ResponseEntity<UserProfileDtoResponse> getUserProfile(OAuth2AuthenticationToken token) {
//            Optional<String> uuidOptional = getUserUuid(token);
//        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponse = this.webClient
//            .get()
//            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
//            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
//            .doOnError(x -> {
//                Logger.warn("error", x);
//            })
//            .block();
//        //check to see if the response is the user does not exist.
//        //if make a request to create the user
//        //try the request again
//        return ResponseEntityUtility.RemoveChunkedHeader(userProfileDtoResponse);
//    }
//
//
//    private ResponseEntity<UserProfileDtoResponse> createUserProfile(
//        Optional<String> uuidOptional) {
//        return this.webClient
//            .get()
//            .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}",
//                Map.of("userId", uuidOptional.get()))
//            .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
//            .doOnError(x -> {
//                Logger.warn("error", x);
//            })
//            .block();
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/current_user")
//    public ResponseEntity<UserProfileDtoResponse> createUserProfile(OAuth2AuthenticationToken token) {
//        Optional<String> uuidOptional = getUserUuid(token);
//
//        ResponseEntity<UserProfileDtoResponse> userProfileDtoResponseResponseEntity = createUserProfile(uuidOptional);
//
//        boolean userDoesNotExist = false;
//        UserProfileDtoResponse userProfileDtoResponse = userProfileDtoResponseResponseEntity.getBody();
//        if(userProfileDtoResponseResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
//            ContentUserProfileErrorResponse contentUserProfileErrorResponse = userProfileDtoResponse.getContent().getError().get(0);
//            if(contentUserProfileErrorResponse.getType().getCode() == ErrorType.userProfile.getCode()){
//                userDoesNotExist = true;
//            }
//        }
//
//        if(userDoesNotExist){
//            userProfileDtoResponseResponseEntity = this.webClient
//                .post()
//                .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/{userId}", Map.of("userId", uuidOptional.get()))
//                .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
//                .doOnError(x -> {
//                    Logger.warn("error", x);
//                })
//                .block();
//
//            if(userProfileDtoResponseResponseEntity.getStatusCode() == HttpStatus.CREATED){
//                userProfileDtoResponseResponseEntity = createUserProfile(uuidOptional);
//            }
//        }
//        return ResponseEntityUtility.RemoveChunkedHeader(userProfileDtoResponseResponseEntity);
//    }


}
