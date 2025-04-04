package com.example.gateway.config;

import com.example.gateway.logger.LoggingFilter;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${GOLANG_SERVICE_URI}")
    private String golangServiceUri;

    @Value("${ADMIN_API_PATH}")
    private String adminApiPath;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
        // **을 .*로 변환하여 정규식 패턴 사용
        String regexPath = adminApiPath.replace("**", "/.*");
        System.out.println("adminApiPath: " + adminApiPath);

        return builder.routes()
                .route(r -> r.path(adminApiPath) // 변환된 정규식 경로 사용
                        .filters(f -> f.filter(loggingFilter)
                                .rewritePath("/api/admin/(?<segment>.*)", "/$\\{segment}")
                        )
                        .uri(golangServiceUri)) // 환경 변수로 URI 설정
                .build();
    }
}
