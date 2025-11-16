package com.it.imdemo.interfaces.websocket;

import com.it.imdemo.application.user.UserApplicationService;
import jakarta.annotation.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Resource
    private UserApplicationService userApplicationService;

    public WebSocketSession getSessionByUserId(Long userId) {
        return userSessions.get(userId);
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        Long userId = getUserIdFromSession(session); // 从 query 或 token
        userSessions.put(userId, session);
        userApplicationService.makeUserOnline(userId);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        Long userId = getUserIdFromSession(session);
        userApplicationService.makeUserOffline(userId);
        userSessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
    }

    private Long getUserIdFromSession(WebSocketSession session) {
        // 从 URI 或 header 获取 userId
        String query = session.getUri().getQuery();
        if(query == null){
            return 0L;
        }
        return Long.valueOf(query.split("=")[1]);
    }
}

