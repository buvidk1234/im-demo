package com.it.imdemo.infrastructure.repository.mapper;

import com.it.imdemo.domain.message.model.Message;
import com.it.imdemo.infrastructure.repository.entity.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 15324
* @description 针对表【messages(聊天消息)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.MessageEntity
*/
@Mapper
public interface MessageMapper extends BaseMapper<MessageEntity> {

    List<Message> findUnreadMessagesByUserId(Long userId);
}




