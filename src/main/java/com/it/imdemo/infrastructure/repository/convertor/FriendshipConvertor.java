package com.it.imdemo.infrastructure.repository.convertor;

import com.it.imdemo.domain.friendship.model.Friendship;
import com.it.imdemo.infrastructure.repository.entity.FriendshipEntity;
import org.springframework.beans.BeanUtils;

import java.lang.classfile.constantpool.FieldRefEntry;

public class FriendshipConvertor {

    public static Friendship toDomain(Object friendshipEntity) {

        Friendship friendship = new Friendship();
        BeanUtils.copyProperties(friendshipEntity, friendship);
        return friendship;
    }

    public static FriendshipEntity toEntity(Friendship friendship) {

        FriendshipEntity friendshipEntity = new FriendshipEntity();
        BeanUtils.copyProperties(friendship, friendshipEntity);

        return friendshipEntity;
    }
}
