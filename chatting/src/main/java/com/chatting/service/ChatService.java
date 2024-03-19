package com.chatting.service;


import com.chatting.domain.Chat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChatService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ChatService(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    /**\
     * 반환 타입이 Flux인 경우엔 조회 -> 채팅방 조회
     * 반환 타입이 Mono인 경우엔 채팅 관련 정보 수정
     */
    public Flux<Chat> findAllChats(){
        return reactiveMongoTemplate.findAll(Chat.class);
    }


    public Mono<Chat> insert(Chat chat){
        return reactiveMongoTemplate.insert(chat, "chat");
    }


}
