package com.shohidulhaque.api_rest_service.endpoints;


import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoRequest;
import com.shohidulhaque.my_people.common_model.data_transfer_object.user_registration_service.PersonRegistrationDtoResponse;
import com.shohidulhaque.my_people.common_utility.ResponseEntityUtility;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
public class UserRegistrationEndPoints {

    static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserRegistrationEndPoints.class);

    final static String RegistrationEndPoint = "http://localhost:8081/api/v1/users/registration";

    @Autowired
    public UserRegistrationEndPoints(WebClient webClient) {
        this.webClient = webClient;
    }

    private WebClient webClient;

    @PostMapping("/registration")
    public ResponseEntity<PersonRegistrationDtoResponse> registerUser(
        PersonRegistrationDtoRequest personRegistrationDtoRequest) {
        ResponseEntity<PersonRegistrationDtoResponse> personRegistrationDtoResponse = this.webClient
                .post()
                .uri("lb://EDGE-SERVICE/api/v1/users/registration")
                .body(Mono.just(personRegistrationDtoRequest), PersonRegistrationDtoRequest.class)
                .exchangeToMono(response -> response.toEntity(PersonRegistrationDtoResponse.class))
                .block();
        return ResponseEntityUtility.RemoveChunkedHeader(personRegistrationDtoResponse);
    }

//    @GetMapping("/users/registration")
//    public HttpEntity<UserRegistrationResponseDTO> registerUser(
//            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient,
//            @AuthenticationPrincipal OidcUser user){
//        UserRegistrationResponseDTO userRegistrationResponseDTO = this.webClient
//                .get()
//                .uri("http://localhost:8081/api/v1/users/registration")
////                .attributes(oauth2AuthorizedClient(authorizedClient))
//                .retrieve()
//                .bodyToMono(UserRegistrationResponseDTO.class)
//                .block();
//        return ResponseEntity.ok(userRegistrationResponseDTO);
//    }


//    @Autowired
//    OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
//
//    @Autowired
//    OAuth2AuthorizedClientRepository authorizedClients;v
}
