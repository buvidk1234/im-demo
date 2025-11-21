package com.it.imdemo.infrastructure.persistence.mapper;

import com.it.imdemo.infrastructure.persistence.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15324
* @description 针对表【users(应用用户)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.UserEntity
*/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}




