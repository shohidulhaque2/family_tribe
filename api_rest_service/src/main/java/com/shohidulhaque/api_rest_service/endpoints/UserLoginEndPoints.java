package com.shohidulhaque.api_rest_service.endpoints;

import com.shohidulhaque.my_people.common_model.data_transfer_object.user_loggedin_status.UserLoggedInStatusResponseDTO;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserLoginEndPoints {

    static org.slf4j.Logger Logger = LoggerFactory.getLogger(UserLoginEndPoints.class);

    private static final Authentication ANONYMOUS_AUTHENTICATION = new AnonymousAuthenticationToken("anonymous",
            "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;

    //CODE NOT NEEDED. ANY THIS IS HOW TO MANUALLY RE_AUTHORIZE THE USER
    @GetMapping("/login")
    public ResponseEntity<Void> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient,
                                      OAuth2AuthenticationToken auth,
                                      @AuthenticationPrincipal OidcUser user,
                                      HttpServletRequest request){
        Authentication authentication = ANONYMOUS_AUTHENTICATION;
        OAuth2AuthorizeRequest.Builder builder = OAuth2AuthorizeRequest.withClientRegistrationId("okta")
                .principal(authentication)
                .attribute(HttpServletRequest.class.getName(), request);
        OAuth2AuthorizeRequest authorizeRequest = builder.build();
        //authorise user if needed
        this.authorizedClientManager.authorize(authorizeRequest);
        return ResponseEntity.ok().build();
    }

    //TODO: check with the authentication server if the user session is still active.
    @GetMapping("/current_user/loggedin")
    public ResponseEntity<UserLoggedInStatusResponseDTO> userLoggedIn(@AuthenticationPrincipal OidcUser user, OAuth2AuthenticationToken token) {
        Logger.info("logged in user " + user);
        if (Objects.isNull(user)) {
            return ResponseEntity.ok(new UserLoggedInStatusResponseDTO(false));
        } else {
            return ResponseEntity.ok(new UserLoggedInStatusResponseDTO(true));
        }
    }

}
