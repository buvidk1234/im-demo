package com.it.imdemo.infrastructure.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.message.MessageRepository;
import com.it.imdemo.domain.message.model.Message;
import com.it.imdemo.infrastructure.repository.entity.MessageEntity;
import com.it.imdemo.infrastructure.repository.mapper.MessageMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @Resource
    private MessageMapper messageMapper;

    @Override
    public void save(Message message) {
        MessageEntity messageEntity = new MessageEntity();
        BeanUtils.copyProperties(message, messageEntity);
        messageMapper.insert(messageEntity);
        message.setId(messageEntity.getId());
    }

    @Override
    public List<Message> findUnreadMessagesByUserId(Long userId) {

        return messageMapper.findUnreadMessagesByUserId(userId);
    }
}
