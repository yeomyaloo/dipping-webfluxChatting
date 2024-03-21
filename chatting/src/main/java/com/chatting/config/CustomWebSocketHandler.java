package com.chatting.config;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
@Slf4j
public class CustomWebSocketHandler implements WebSocketHandler {

    StringBuilder sb = new StringBuilder();

    private final Sinks.Many<String> sink;

    public CustomWebSocketHandler(Sinks.Many<String> sink) {
        this.sink = sink;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        var output = session.receive()
                .map(e -> e.getPayloadAsText())
                .map(e -> {
                    try{
                        JSONObject json = new JSONObject(e); // 클라이언트에서 전송된 JSON 문자열을 파싱합니다.
                        String username = json.getString("username");
                        String message = json.getString("message");
                        if (username.isBlank()) {
                            username="익명 사용자";
                        }

                        sb.setLength(0); // StringBuilder 초기화
                        sb.append(username);
                        sb.append(" : ");
                        sb.append(message);

                        return sb.toString();

                    } catch (JSONException ex){
                        ex.printStackTrace();
                        //exception 처리 !
                        return "메시지 처리 중 오류 발생";
                    }
                });
        //메시지 처리 오류나면 예외를 발생시킵니다.
        output.subscribe(s -> sink.emitNext(s, Sinks.EmitFailureHandler.FAIL_FAST));

        return session.send(sink.asFlux().map(session::textMessage));
    }
}
