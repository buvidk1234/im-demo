package com.it.imdemo.infrastructure.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.message.ConversationRepository;
import com.it.imdemo.domain.message.model.Conversation;
import com.it.imdemo.infrastructure.repository.convertor.ConversationConverter;
import com.it.imdemo.infrastructure.repository.entity.ConversationEntity;
import com.it.imdemo.infrastructure.repository.mapper.ConversationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConversationRepositoryImpl implements ConversationRepository {

    @Resource
    private ConversationMapper conversationMapper;

    @Override
    public Optional<Conversation> findById(Long conversationId) {
        ConversationEntity conversationEntity = new LambdaQueryChainWrapper<>(conversationMapper).eq(ConversationEntity::getId, conversationId).one();
        return Optional.ofNullable(conversationEntity).map(ConversationConverter::toDomain);
    }

    @Override
    public void save(Conversation conversation) {
        ConversationEntity conversationEntity = ConversationConverter.toEntity(conversation);
        conversationEntity.setUserAId(Math.min(conversation.getUserAId(), conversation.getUserBId()));
        conversationEntity.setUserBId(Math.max(conversation.getUserAId(), conversation.getUserBId()));
        conversationMapper.insertOrUpdate(conversationEntity);
        conversation.setId(conversationEntity.getId());
    }

    @Override
    public Optional<Conversation> findByTypeAndSenderIdAndReceiverId(Integer type, Long senderId, Long receiverId) {
        ConversationEntity conversationEntity=null;
        if(type == 1) {
            conversationEntity = new LambdaQueryChainWrapper<>(conversationMapper)
                    .eq(ConversationEntity::getType, type)
                    .eq(ConversationEntity::getGroupId, receiverId)
                    .one();
        } else if(type == 0) {
            conversationEntity = new LambdaQueryChainWrapper<>(conversationMapper)
                    .eq(ConversationEntity::getType, type)
                    .eq(ConversationEntity::getUserAId, Math.min(senderId, receiverId))
                    .eq(ConversationEntity::getUserBId, Math.max(senderId, receiverId))
                    .one();
        }
        return Optional.ofNullable(conversationEntity).map(ConversationConverter::toDomain);
    }
}
