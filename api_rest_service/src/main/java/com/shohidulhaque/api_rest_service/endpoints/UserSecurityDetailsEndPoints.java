package com.shohidulhaque.api_rest_service.endpoints;

import com.shohidulhaque.my_people.common_model.user_security_information.UserSecurityInformationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/v1")
public class UserSecurityDetailsEndPoints {

    WebClient webClient;
    public UserSecurityDetailsEndPoints(WebClient webClient){ this.webClient = webClient; }

    @GetMapping("/users/current_user/security_details")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserSecurityInformationResponseDTO> userSecurityInformation(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return ResponseEntity.ok().build();
    }

}
