package com.example.gateway.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Value("${GOLANG_SERVICE_URI}")
    private String golangServiceUri;

    @Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 요청 URI를 로깅
                System.out.println("Request URI: " + exchange.getRequest().getURI());

                // 응답 코드 로깅
                exchange.getResponse().beforeCommit(() -> {
                    System.out.println("Response status: " + exchange.getResponse().getStatusCode());
                    return Mono.empty();
                });

                // 다음 필터로 요청 전달
                return chain.filter(exchange);
            }
        };
    }
}
