package com.it.imdemo.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.friendship.model.Friendship;
import com.it.imdemo.domain.friendship.repository.FriendshipRepository;
import com.it.imdemo.infrastructure.repository.convertor.FriendshipConvertor;
import com.it.imdemo.infrastructure.repository.entity.FriendshipEntity;
import com.it.imdemo.infrastructure.repository.mapper.FriendshipMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {

    @Resource
    private FriendshipMapper friendshipMapper;

    @Override
    public void save(Friendship friendship) {

        FriendshipEntity friendshipEntity = FriendshipConvertor.toEntity(friendship);
        friendshipMapper.insertOrUpdate(friendshipEntity);
    }

    @Override
    public Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId) {

        FriendshipEntity friendshipEntity = new LambdaQueryChainWrapper<>(friendshipMapper)
                .eq(FriendshipEntity::getUserId, Math.min(userId, friendId))
                .eq(FriendshipEntity::getFriendId, Math.max(userId, friendId))
                .one();

        return Optional.ofNullable(friendshipEntity).map(FriendshipConvertor::toDomain);
    }

    @Override
    public List<Friendship> findAllFriendsByUserId(Long userId) {
        List<FriendshipEntity> list = new LambdaQueryChainWrapper<>(friendshipMapper)
                .and(wrapper -> wrapper
                        .eq(FriendshipEntity::getUserId, userId)
                        .or()
                        .eq(FriendshipEntity::getFriendId, userId))
                .eq(FriendshipEntity::getStatus, 1).list();
        list.forEach(friendshipEntity -> {
            Long friendId = friendshipEntity.getUserId().equals(userId) ? friendshipEntity.getFriendId() : friendshipEntity.getUserId();
            friendshipEntity.setFriendId(friendId);
            friendshipEntity.setUserId(userId);
        });
        return list.stream().map(FriendshipConvertor::toDomain).toList();
    }
}
