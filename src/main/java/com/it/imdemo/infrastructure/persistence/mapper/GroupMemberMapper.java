package com.it.imdemo.infrastructure.persistence.mapper;

import com.it.imdemo.infrastructure.persistence.entity.GroupMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15324
* @description 针对表【group_members(群成员关系)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.GroupMemberEntity
*/
@Mapper
public interface GroupMemberMapper extends BaseMapper<GroupMemberEntity> {

}




