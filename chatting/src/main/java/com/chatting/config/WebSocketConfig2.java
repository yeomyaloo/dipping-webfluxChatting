package com.chatting.config;

import com.chatting.service.ChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;


import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableWebFlux
public class WebSocketConfig2 implements WebFluxConfigurer {

    public HandlerMapping webSocketMapping(WebSocketHandler handler){
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/chat", handler);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(map);
        mapping.setOrder(1);
        return mapping;
    }

    @Bean
    public WebSocketHandler webSocketHandler(ChatService chatService) {
        return new ChatWebSocketHandler(chatService);
    }
}
