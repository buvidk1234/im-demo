package com.it.imdemo.domain.message;

import com.it.imdemo.domain.message.model.Message;

import java.util.List;

public interface WebSocketGateway {
    void pushUnreadMessagesToUser(Long userId, List<Message> unreadMessages);
}
