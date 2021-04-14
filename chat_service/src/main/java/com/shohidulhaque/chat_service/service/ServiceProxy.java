package com.shohidulhaque.chat_service.service;

import com.shohidulhaque.chat_service.data_model.UserInvitation;
import com.shohidulhaque.my_people.common_model.data_transfer_object.profile_service.UserProfileDtoResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ServiceProxy {
    //==============================================================================================
    static org.slf4j.Logger Logger = LoggerFactory.getLogger(ServiceProxy.class);
    //==============================================================================================
    WebClient webClient;
    //==============================================================================================
    @Autowired
    public ServiceProxy(WebClient webClient){
        this.webClient = webClient;
    }
    //==============================================================================================
    public  Optional<UUID> doesMemberExist(String email)  throws IOException {
        //call registry service
        ResponseEntity<UserProfileDtoResponse> responseEntity =
            this.webClient
                .get()
                .uri("lb://USER-PROFILE-SERVICE/api/v1/user-profile-service/users/{profileId}", email)
                .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
                .doOnError(x -> Logger.warn("error", x))
                .block();
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return Optional.empty();
        } else {

        }
        return Optional.empty();
    }
    //==============================================================================================
    @Transactional
    public boolean sendInvitationToUser(UUID invitedUserUuid, UserInvitation userInvitation) throws IOException {
        ResponseEntity<UserProfileDtoResponse> responseEntity =
            this.webClient
                .post()
                .uri("lb://USER-MAIL-BOX-SERVICE/api/v1/user-mail-box-service/users/{uuid}", invitedUserUuid.toString())
                .exchangeToMono(response -> response.toEntity(UserProfileDtoResponse.class))
                .doOnError(x -> Logger.warn("error", x))
                .block();
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return true;
        } else {

        }
        return false;
    }
    //==============================================================================================
}
