package com.it.imdemo.domain.user;

import com.it.imdemo.domain.user.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> getByUsername(String username);

    Optional<User> getById(Long userId);
}
