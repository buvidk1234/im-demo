package com.it.imdemo.infrastructure.persistence.mapper;

import com.it.imdemo.infrastructure.persistence.entity.ConversationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15324
* @description 针对表【conversations(私聊与群聊统一会话)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.ConversationEntity
*/
@Mapper
public interface ConversationMapper extends BaseMapper<ConversationEntity> {

}




