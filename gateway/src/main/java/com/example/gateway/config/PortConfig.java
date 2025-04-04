package com.example.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class PortConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Value("${GATEWAY_PORT}")
    private String gatewayPort;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        try {
            // 포트 값이 올바른지 확인 후 설정
            int port = Integer.parseInt(gatewayPort);
            factory.setPort(port);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number in GATEWAY_PORT environment variable: " + gatewayPort, e);
        }
    }
}
