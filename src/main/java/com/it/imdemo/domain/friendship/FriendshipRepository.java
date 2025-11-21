package com.it.imdemo.domain.friendship;

import com.it.imdemo.domain.friendship.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository {
    void save(Friendship friendship);

    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    List<Friendship> findAllFriendsByUserId(Long userId);
}
