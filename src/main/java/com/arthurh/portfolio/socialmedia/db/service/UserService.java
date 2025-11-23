package com.arthurh.portfolio.socialmedia.db.service;

import com.arthurh.portfolio.socialmedia.db.model.User;
import com.arthurh.portfolio.socialmedia.db.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepo userRepo;

    public Optional<User> findByUniqueId(String uniqueId) {
        return userRepo.findByUniqueIdForUpdate(uniqueId);
    }

    public User save(User user) {
        return userRepo.save(user);
    }
}
