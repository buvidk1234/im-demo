package com.it.imdemo.application.friendship;

import com.it.imdemo.domain.friendship.model.Friendship;
import com.it.imdemo.domain.friendship.repository.FriendshipRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipApplicationService {

    @Resource
    private FriendshipRepository friendshipRepository;

    public void addFriend(Long userId, Long friendId) {
        friendshipRepository.findByUserIdAndFriendId(userId, friendId).ifPresent(f -> {
            throw new IllegalArgumentException("Already friends");
        });
        Friendship friendship = Friendship.create(userId, friendId);
        friendshipRepository.save(friendship);
    }

    public void agreeFriend(Long userId, Long friendId) {
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));
        friendship.agreeFriendship();
        friendshipRepository.save(friendship);

    }

    public List<Long> listFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAllFriendsByUserId(userId);
        return friendships.stream().map(Friendship::getFriendId).toList();
    }

    public void removeFriend(Long userId, Long friendId) {
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));
        friendship.removeFriendship();
        friendshipRepository.save(friendship);
    }
}
