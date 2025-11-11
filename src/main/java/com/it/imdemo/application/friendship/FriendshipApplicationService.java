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
        Friendship friendship = new Friendship();
        friendshipRepository.findByUserIdAndFriendId(userId, friendId).ifPresent(f -> {
            throw new IllegalArgumentException("Already friends");
        });
        friendship.addFriendship(userId, friendId);
        friendshipRepository.save(friendship);
    }

    public void agreeFriend(Long userId, Long friendId) {
        Optional<Friendship> optionalFriendship = friendshipRepository.findByUserIdAndFriendId(userId, friendId);
        if (optionalFriendship.isPresent()) {
            Friendship friendship = optionalFriendship.get();
            friendship.agreeFriendship();
            friendshipRepository.save(friendship);
        } else {
            throw new IllegalArgumentException("Friend request not found");
        }
    }
    public List<Long> listFriends(Long userId) {
        List<Friendship> friendships = friendshipRepository.findAllFriendsByUserId(userId);
        return friendships.stream().map(Friendship::getFriendId).toList();
    }
}
