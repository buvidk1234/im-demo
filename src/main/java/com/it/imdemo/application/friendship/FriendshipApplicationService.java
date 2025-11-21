package com.it.imdemo.application.friendship;

import com.it.imdemo.domain.friendship.model.Friendship;
import com.it.imdemo.domain.friendship.FriendshipRepository;
import com.it.imdemo.commons.exception.BizErrorCode;
import com.it.imdemo.commons.exception.BizException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipApplicationService {

    @Resource
    private FriendshipRepository friendshipRepository;

    public void addFriend(Long userId, Long friendId) {
        friendshipRepository.findByUserIdAndFriendId(userId, friendId).ifPresent(f -> {
            throw new BizException(BizErrorCode.FRIENDSHIP_ALREADY_EXISTS);
        });
        Friendship friendship = Friendship.create(userId, friendId);
        friendshipRepository.save(friendship);
    }

    public void agreeFriend(Long userId, Long friendId) {
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new BizException(BizErrorCode.FRIENDSHIP_NOT_REQUESTED));
        friendship.agreeFriendship();
        friendshipRepository.save(friendship);

    }

    public List<Long> listFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAllFriendsByUserId(userId);
        return friendships.stream().map(Friendship::getFriendId).toList();
    }

    public void removeFriend(Long userId, Long friendId) {
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new BizException(BizErrorCode.FRIENDSHIP_NOT_FOUND));
        friendship.removeFriendship();
        friendshipRepository.save(friendship);
    }
}
