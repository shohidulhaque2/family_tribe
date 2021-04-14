package com.shohidulhaque.api_rest_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceDtoResponse;
import com.shohidulhaque.my_people.common_model.data_transfer_object.chat_service.ChatSpaceInvitationDtoRequest;
import com.shohidulhaque.my_people.common_utility.ResponseEntityUtility;
import com.shohidulhaque.my_people.common_utility.UuidGrantedAuthority;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/users")
@RestController
public class UserChatSpaceEndPoints {

    static Logger Logger = LoggerFactory.getLogger(UserChatSpaceEndPoints.class);

    WebClient webClient;

    public UserChatSpaceEndPoints(WebClient webClient) {
        this.webClient = webClient;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/current_user/chatspaces")
    public ResponseEntity<ChatSpaceDtoResponse> createChatSpace(@RequestBody ChatSpaceDtoRequest createChatSpaceRequestDto, OAuth2AuthenticationToken token) {

        Optional<String> uuidOptional = getUserUuid(token);

        if (uuidOptional.isEmpty()) {
            //TODO: give a reasonable response here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        //create user lb://EDGE-SERVICE/api/v1/chatspaces/users/{userId}
        ////          lb://EDGE-SERVICE/api/v1/chatspaces/users/{userId}/chatspaces
        ResponseEntity<ChatSpaceDtoResponse> createChatSpaceResponseDto = this.webClient
            .post()
            .uri("lb://EDGE-SERVICE/api/v1/users/{userId}/chatspaces", Map.of("userId", uuidOptional.get()))
            .body(Mono.just(createChatSpaceRequestDto), ChatSpaceDtoRequest.class)
            .exchangeToMono(response -> response.toEntity(ChatSpaceDtoResponse.class))
            .doOnError(x -> {
                Logger.warn("error", x);
            })
            .block();

        //check to see if the response is the user does not exist.
        //if make a request to create the user
        //try the request again

        return ResponseEntityUtility.RemoveChunkedHeader(createChatSpaceResponseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current_user/chatspaces")
    public ResponseEntity<ChatSpaceDtoResponse> getChatSpace(OAuth2AuthenticationToken token) {

        Optional<String> uuidOptional = getUserUuid(token);

        if (uuidOptional.isEmpty()) {
            //TODO: give a reasonable response here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ResponseEntity<ChatSpaceDtoResponse> responseEntity = this.webClient
            .get()
            .uri("lb://EDGE-SERVICE/api/v1/users/{userId}/chatspaces", Map.of("userId", uuidOptional.get()))
            .exchangeToMono(response -> response.toEntity(ChatSpaceDtoResponse.class))
            .doOnError(x -> {
                Logger.warn("error", x);
            })
            .block();
        return ResponseEntityUtility.RemoveChunkedHeader(responseEntity);
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/current_user/invitation/{chatSpaceId}")
//    public ResponseEntity<ChatSpaceDtoResponse> inviteUser(OAuth2AuthenticationToken token) {
//
//        Optional<String> uuidOptional = getUserUuid(token);
//
//        if (uuidOptional.isEmpty()) {
//            //TODO: give a reasonable response here
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//        ResponseEntity<ChatSpaceDtoResponse> responseEntity = this.webClient
//            .get()
//            .uri("lb://EDGE-SERVICE/api/v1/users/{userId}/chatspaces/{chatSpaceId}", Map.of("userId", uuidOptional.get()))
//            .exchangeToMono(response -> response.toEntity(ChatSpaceDtoResponse.class))
//            .doOnError(x -> {
//                Logger.warn("error", x);
//            })
//            .block();
//        return ResponseEntityUtility.RemoveChunkedHeader(responseEntity);
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/current_user/chatspaces/members")
    public ResponseEntity<ChatSpaceDtoResponse> invitePersonToChatSpace(
        OAuth2AuthenticationToken token,
        @RequestBody ChatSpaceInvitationDtoRequest chatSpaceInvitationDtoRequest){
        Optional<String> uuidOptional = getUserUuid(token);
        if (uuidOptional.isEmpty()) {
            //TODO: give a reasonable response here
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseEntity<ChatSpaceDtoResponse> responseEntity = this.webClient
            .post()
            .uri("lb://EDGE-SERVICE/api/v1/chat-service/users/{userId}/chatspaces/members", Map.of("userId", uuidOptional.get()))
            .body(Mono.just(chatSpaceInvitationDtoRequest), ChatSpaceInvitationDtoRequest.class)
            .exchangeToMono(response -> response.toEntity(ChatSpaceDtoResponse.class))
            .doOnError(x -> {
                Logger.warn("error", x);
            })
            .block();
        return ResponseEntityUtility.RemoveChunkedHeader(responseEntity);
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
}
