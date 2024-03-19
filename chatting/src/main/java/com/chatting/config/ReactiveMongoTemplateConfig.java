package com.chatting.config;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class ReactiveMongoTemplateConfig {

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }


    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, "test");
    }
}
