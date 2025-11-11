package com.it.imdemo.infrastructure.repository.mapper;

import com.it.imdemo.infrastructure.repository.entity.FriendshipEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15324
* @description 针对表【friendships(好友关系与状态)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.FriendshipEntity
*/
@Mapper
public interface FriendshipMapper extends BaseMapper<FriendshipEntity> {

}




