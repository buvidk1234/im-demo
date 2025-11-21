package com.it.imdemo;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.infrastructure.persistence.entity.UserEntity;
import com.it.imdemo.infrastructure.persistence.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private UserMapper userMapper;

    @Test
    void dbTest() {
        UserEntity userEntity = new LambdaQueryChainWrapper<>(userMapper).eq(UserEntity::getUsername, "user").one();
        System.out.println(userEntity);
    }
}
