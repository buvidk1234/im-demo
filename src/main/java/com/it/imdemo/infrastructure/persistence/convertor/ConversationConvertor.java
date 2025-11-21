package com.it.imdemo.infrastructure.persistence.convertor;

import com.it.imdemo.domain.message.model.Conversation;
import com.it.imdemo.infrastructure.persistence.entity.ConversationEntity;
import org.springframework.beans.BeanUtils;

public class ConversationConvertor {
    public static Conversation toDomain(ConversationEntity conversationEntity) {
        Conversation conversation = new Conversation();
        BeanUtils.copyProperties(conversationEntity, conversation);
        return conversation;
    }
    public static ConversationEntity toEntity(Conversation conversation) {
        ConversationEntity conversationEntity = new ConversationEntity();
        BeanUtils.copyProperties(conversation, conversationEntity);
        return conversationEntity;
    }
}
