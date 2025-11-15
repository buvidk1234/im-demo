package com.it.imdemo.infrastructure.repository.convertor;

import com.it.imdemo.domain.user.model.User;
import com.it.imdemo.infrastructure.repository.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class UserConvertor {

    public static User toDomain(UserEntity userEntity) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        user.getContactInformation().setEmail(userEntity.getEmail());
        user.getContactInformation().setPhone(userEntity.getPhone());
        return user;
    }
    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEmail(user.getContactInformation().getEmail());
        userEntity.setPhone(user.getContactInformation().getPhone());
        return userEntity;
    }
}
