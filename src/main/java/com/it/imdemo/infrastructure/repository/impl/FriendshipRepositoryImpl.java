package com.it.imdemo.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.it.imdemo.domain.friendship.model.Friendship;
import com.it.imdemo.domain.friendship.repository.FriendshipRepository;
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
        FriendshipEntity friendshipEntity = new FriendshipEntity();
        BeanUtils.copyProperties(friendship, friendshipEntity);
        friendshipMapper.insertOrUpdate(friendshipEntity);
    }

    @Override
    public Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId) {
        Long a = Math.min(userId, friendId);
        Long b = Math.max(userId, friendId);
        FriendshipEntity friendshipEntity = new LambdaQueryChainWrapper<>(friendshipMapper)
                .eq(FriendshipEntity::getUserId, a)
                .eq(FriendshipEntity::getFriendId, b)
                .one();
        if (friendshipEntity != null) {
            Friendship friendship = new Friendship();
            BeanUtils.copyProperties(friendshipEntity, friendship);
            return Optional.of(friendship);
        }
        return Optional.empty();
    }

    @Override
    public List<Friendship> findAllFriendsByUserId(Long userId) {
        List<FriendshipEntity> list = new LambdaQueryChainWrapper<>(friendshipMapper)
                .and(wrapper -> wrapper
                        .eq(FriendshipEntity::getUserId, userId)
                        .or()
                        .eq(FriendshipEntity::getFriendId, userId))
                .eq(FriendshipEntity::getStatus, 1).list();
        return list.stream().map(friendshipEntity -> {
            Friendship friendship = new Friendship();
            BeanUtils.copyProperties(friendshipEntity, friendship);
            Long friendId = friendshipEntity.getUserId().equals(userId) ? friendshipEntity.getFriendId() : friendshipEntity.getUserId();
            friendship.setFriendId(friendId);
            friendship.setUserId(userId);
            return friendship;
        }).toList();
    }
}
