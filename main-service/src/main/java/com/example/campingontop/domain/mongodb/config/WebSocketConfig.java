package com.example.campingontop.domain.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /*
        WebSocket과 STOMP 메시징을 위한 설정을 추가.
        이 설정은 클라이언트가 서버에 연결할 수 있는 엔드포인트와 메시지 브로커를 설정.
     */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 브로드캐스팅할 때 사용할 prefix 설정
        registry.enableSimpleBroker("/topic");
        // 클라이언트에서 메시지를 보낼 때 사용할 prefix 설정
        registry.setApplicationDestinationPrefixes("/app");
    }
}
