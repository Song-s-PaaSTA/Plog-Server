package com.songspasssta.apigatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;

@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    private final static String[] PERMIT_ALL_ANTPATTERNS = {
            "/", "/csrf",
            "/member-service/login",
            "/?*-service/actuator/?*", "/actuator/?*",
            "/v3/api-docs/**", "/?*-service/v3/api-docs", "**/configuration/*", "/swagger*/**", "/webjars/**"
    };

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http, ReactiveAuthorizationManager<AuthorizationContext> check) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeExchange(auth -> auth
                        .pathMatchers(PERMIT_ALL_ANTPATTERNS).permitAll()
                )
                .build();
    }
}
