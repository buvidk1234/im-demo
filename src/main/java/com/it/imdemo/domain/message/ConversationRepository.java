package com.it.imdemo.domain.message;

import com.it.imdemo.domain.message.model.Conversation;

import java.util.Optional;

public interface ConversationRepository {
    Optional<Conversation> findById(Long conversationId);

    void save(Conversation conversation);

    Optional<Conversation> findByTypeAndSenderIdAndReceiverId(Integer type, Long senderId, Long receiverId);
}
