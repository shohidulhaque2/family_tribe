package com.shohidulhaque.api_rest_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_mailbox_service.UserMailboxDtoResponse;
import com.shohidulhaque.my_people.common_utility.ResponseEntityUtility;
import com.shohidulhaque.my_people.common_utility.UuidGrantedAuthority;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequestMapping("/api/v1/mailbox")
@RestController
public class UserMailboxEndpoints {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    WebClient webClient;

    @Autowired
    public UserMailboxEndpoints(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/current_user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserMailboxDtoResponse> getMailbox(OAuth2AuthenticationToken token) {
        logger.info("Same Span");
        Optional<String> uuidOptional = getUserUuid(token);
        ResponseEntity<UserMailboxDtoResponse> responseEntity = this.webClient
            .get()
            .uri("lb://EDGE-SERVICE/api/v1/user-mailbox-service/users/mailbox/{userMailboxUuid}", uuidOptional.get())
            .exchangeToMono(response -> response.toEntity(UserMailboxDtoResponse.class))
            .doOnError(x -> logger.warn("error", x))
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
}
