package com.it.imdemo.infrastructure.persistence.convertor;

import com.it.imdemo.domain.friendship.model.Friendship;
import com.it.imdemo.infrastructure.persistence.entity.FriendshipEntity;
import org.springframework.beans.BeanUtils;

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
