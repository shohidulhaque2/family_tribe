package com.shohidulhaque.my_people.discovery_service.config;

//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.reactive.function.client.WebClient;
//https://github.com/benwilcock/spring-cloud-gateway-demo/tree/master/security-gateway

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
//https://developer.okta.com/blog/2019/05/22/java-microservices-spring-boot-spring-cloud
//https://github.com/oktadeveloper/java-microservices-examples
//https://spring.io/blog/2019/08/16/securing-services-with-spring-cloud-gateway

@Configuration
public class Routes {
    //    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/yahoo").uri("https://www.yahoo.com"))
//                .build();
//    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           TokenRelayGatewayFilterFactory filterFactory) {
        return builder.routes()
//                .route("car-service", r -> r.path("/cars")
//                        .filters(f -> f.filter(filterFactory.apply()))
//                        // .filtuser-profile-serviceers(f -> f.hystrix(c -> c.setName("carsFallback")
//                        // .setFallbackUri("forward:/cars-fallback")))
//                        .uri("lb://car-service"))
//
                .route("REGISTRY-SERVICE", r -> r.path("/api/v1/users/registration").uri("lb://USER-REGISTRY-SERVICE"))
                .route("CHAT_SPACES", r -> r.path("/api/v1/users/{userUuid}/chatspaces").uri("lb://CHAT-SERVICE"))
                .route("PROFILE-SERVICE", r -> r.path("/api/v1/user-profile-service/**").uri("lb://USER-PROFILE-SERVICE"))
                .route("CHAT-SERVICE", r -> r.path("/api/v1/chat-service/**").uri("lb://CHAT-SERVICE"))
                .route("MAILBOX-SERVICE", r -> r.path("/api/v1/user-mailbox-service/**").uri("lb://USER-MAILBOX-SERVICE"))
            .build();
    }
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
