package com.songspasssta.apigatewayservice.config;

import com.songspasssta.apigatewayservice.auth.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.songspasssta.common.auth.GatewayConstants.GATEWAY_AUTH_HEADER;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final TokenProvider tokenProvider;

    @Autowired
    public AuthorizationHeaderFilter(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            log.info("AuthorizationHeaderFilter Start: request -> {}", exchange.getRequest());

            final HttpHeaders headers = request.getHeaders();

            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            final String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);

            final String accessToken = authorizationHeader.replace("Bearer", "");

            if (!tokenProvider.validToken(accessToken)) {
                return onError(exchange, "Invalid access token", HttpStatus.UNAUTHORIZED);
            }

            final String subject = tokenProvider.getSubject(accessToken);

            final ServerHttpRequest newRequest = request.mutate()
                    .header(GATEWAY_AUTH_HEADER, subject)
                    .build();

            log.info("AuthorizationHeaderFilter End");
            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus httpStatus) {
        log.error(errorMsg);

        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

    static class Config {

    }
}
