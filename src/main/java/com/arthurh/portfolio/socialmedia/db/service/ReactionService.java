package com.arthurh.portfolio.socialmedia.db.service;

import com.arthurh.portfolio.socialmedia.db.model.Reaction;
import com.arthurh.portfolio.socialmedia.db.repository.ReactionRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionService {
    final ReactionRepo reactionRepo;

    public Page<Reaction> findAll(@Nullable Pageable pageable) {
        return reactionRepo.findAllByInactiveAtIsNull(pageable);
    }

    public Optional<Reaction> findByName(String name) {
        return reactionRepo.findByName(name);
    }
}
