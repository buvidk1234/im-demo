package com.it.imdemo.domain.message;

import com.it.imdemo.domain.message.model.Message;

import java.util.List;

public interface MessageRepository {
    void save(Message message);

    List<Message> findUnreadMessagesByUserId(Long userId);

}
