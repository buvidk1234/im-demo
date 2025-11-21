package com.it.imdemo.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.user.UserRepository;
import com.it.imdemo.domain.user.model.User;
import com.it.imdemo.infrastructure.persistence.convertor.UserConvertor;
import com.it.imdemo.infrastructure.persistence.entity.UserEntity;
import com.it.imdemo.infrastructure.persistence.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserMapper userMapper;

    @Override
    public void save(User user) {
        UserEntity userEntity = UserConvertor.toEntity(user);
        userMapper.insertOrUpdate(userEntity);
    }

    @Override
    public Optional<User> getByUsername(String username) {
//        userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, username));
        UserEntity userEntity = new LambdaQueryChainWrapper<>(userMapper).eq(UserEntity::getUsername, username).one();
        return Optional.ofNullable(userEntity).map(UserConvertor::toDomain);
    }

    @Override
    public Optional<User> getById(Long userId) {
        UserEntity userEntity = new LambdaQueryChainWrapper<>(userMapper).eq(UserEntity::getId, userId).one();
        return Optional.ofNullable(userEntity).map(UserConvertor::toDomain);
    }
}
