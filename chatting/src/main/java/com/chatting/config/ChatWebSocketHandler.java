package com.chatting.config;

import com.chatting.domain.Chat;
import com.chatting.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


//@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ChatService chatService;

    public ChatWebSocketHandler(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<Chat> chatFlux = chatService.findAllChats();
        return session.send(chatFlux.map(chat -> session.textMessage(chat.toString())));
    }


}
