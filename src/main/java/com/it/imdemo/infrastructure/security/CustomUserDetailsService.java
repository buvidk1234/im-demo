package com.it.imdemo.infrastructure.security;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.infrastructure.persistence.entity.UserEntity;
import com.it.imdemo.infrastructure.persistence.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 使用 MyBatis 查库
        UserEntity userEntity = new LambdaQueryChainWrapper<>(userMapper).eq(UserEntity::getUsername, username).one();

        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 2. 转换为 Spring Security 的 UserDetails 对象
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPasswordHash()) // 必须是加密后的
                .roles("user")            // 角色
                .disabled(userEntity.getStatus() == 0) // 根据状态设置是否禁用
                .build();
    }
}
