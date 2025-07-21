package com.Chat.Chatapp.Websocketcon;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class Websocketconfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println("4");
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("9");
        registry.addEndpoint("/ws").setAllowedOrigins("http://127.0.0.1:5500").withSockJS();

    }
}
