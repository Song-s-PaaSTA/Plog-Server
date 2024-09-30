package com.songspasssta.apigatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {

    private final static String[] PERMIT_ALL_ANTPATTERNS = {
            "/", "/csrf",
            "/?*-service/actuator/?*", "/actuator/?*",
            "/v3/api-docs/**", "/?*-service/v3/api-docs", "/swagger*/**", "/webjars/**"
    };

    private final static String LOGIN_ANTPATTERNS = "/member-service/api/v1/login/**";

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http, ReactiveAuthorizationManager<AuthorizationContext> check) {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("*");
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    config.setAllowCredentials(true);
                    return config;
                }))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(auth -> auth
                        .pathMatchers(PERMIT_ALL_ANTPATTERNS).permitAll()
                        .pathMatchers(HttpMethod.POST, LOGIN_ANTPATTERNS).permitAll()
                        .anyExchange().permitAll()
                )
                .build();
    }
}
