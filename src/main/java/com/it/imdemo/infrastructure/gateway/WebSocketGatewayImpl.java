package com.it.imdemo.infrastructure.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.imdemo.domain.message.WebSocketGateway;
import com.it.imdemo.domain.message.model.Message;
import com.it.imdemo.interfaces.websocket.MyWebSocketHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Repository
public class WebSocketGatewayImpl implements WebSocketGateway {

    @Resource
    private MyWebSocketHandler webSocketHandler;

    @Resource
    private ObjectMapper objectMapper; // Spring Boot 默认注入

    @Override
    public void pushUnreadMessagesToUser(Long userId, List<Message> unreadMessages) {
        WebSocketSession session = webSocketHandler.getSessionByUserId(userId);
        if (session != null && session.isOpen()) {
            try {
                for (Message msg : unreadMessages) {
                    session.sendMessage(new TextMessage(convertMessageToJson(msg)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String convertMessageToJson(Message message) {
        try {
            return objectMapper.writeValueAsString(message); // 使用 Jackson
        } catch (JsonProcessingException e) {
            throw new RuntimeException("消息序列化失败", e);
        }
    }
}
