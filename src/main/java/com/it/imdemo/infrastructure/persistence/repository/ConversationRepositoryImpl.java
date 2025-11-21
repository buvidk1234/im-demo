package com.it.imdemo.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.message.ConversationRepository;
import com.it.imdemo.domain.message.model.Conversation;
import com.it.imdemo.infrastructure.persistence.convertor.ConversationConvertor;
import com.it.imdemo.infrastructure.persistence.entity.ConversationEntity;
import com.it.imdemo.infrastructure.persistence.mapper.ConversationMapper;
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
        return Optional.ofNullable(conversationEntity).map(ConversationConvertor::toDomain);
    }

    @Override
    public void save(Conversation conversation) {
        ConversationEntity conversationEntity = ConversationConvertor.toEntity(conversation);
        // 存储逻辑可以存的时候统一处理userAId和userBId的顺序，或者在查询的时候处理，这里选择存储的时候处理
        // 无论如何都是对业务层透明的
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
        return Optional.ofNullable(conversationEntity).map(ConversationConvertor::toDomain);
    }
}
