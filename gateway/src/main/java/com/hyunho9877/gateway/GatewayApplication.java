package com.hyunho9877.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("freeBoard", r ->
                        r
                                .path("/freeboard/**")
                                .filters(f -> f.filter(filterFactory.apply())
                                        .rewritePath("/freeboard/(?<segment>.*)", "/${segment}"))
                                .uri("lb://FREEBOARD"))
                .route("infoBoard", r ->
                        r
                                .path("/infoboard/**")
                                .filters(f -> f.filter(filterFactory.apply())
                                        .rewritePath("/freeboard/(?<segment>.*)", "/${segment}"))
                                .uri("lb://INFOBOARD"))
                .route("doodle", r ->
                        r
                                .path("/doodle/**")
                                .filters(f -> f.filter(filterFactory.apply())
                                        .rewritePath("/doodle/(?<segment>.*)", "/${segment}"))
                                .uri("lb://DOODLE"))
                .route("critic", r ->
                        r
                                .path("/critic/**")
                                .filters(f -> f.filter(filterFactory.apply())
                                        .rewritePath("/critic/(?<segment>.*)", "/${segment}"))
                                .uri("lb://CRITIC"))
                .route("account", r ->
                        r
                                .path("/account/**")
                                .filters(f -> f.filter(filterFactory.apply())
                                        .rewritePath("/account/(?<segment>.*)", "/${segment}"))
                                .uri("lb://ACCOUNT"))
                .build();
    }

}
