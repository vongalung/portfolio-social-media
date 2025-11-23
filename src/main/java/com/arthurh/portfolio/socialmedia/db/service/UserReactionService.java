package com.arthurh.portfolio.socialmedia.db.service;

import com.arthurh.portfolio.socialmedia.db.model.User;
import com.arthurh.portfolio.socialmedia.db.model.UserReaction;
import com.arthurh.portfolio.socialmedia.db.repository.UserReactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserReactionService {
    final UserReactionRepo userReactionRepo;

    public Optional<UserReaction> findByContentAndUser(UUID contentId, User user) {
        return userReactionRepo.findByContent_IdAndUser_Id(contentId, user.getId());
    }
}
