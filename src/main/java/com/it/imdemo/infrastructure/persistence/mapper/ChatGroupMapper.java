package com.it.imdemo.infrastructure.persistence.mapper;

import com.it.imdemo.infrastructure.persistence.entity.ChatGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15324
* @description 针对表【chat_groups(聊天群组)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.ChatGroupEntity
*/
@Mapper
public interface ChatGroupMapper extends BaseMapper<ChatGroupEntity> {

}




