package com.arthurh.portfolio.socialmedia.service;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import com.arthurh.portfolio.socialmedia.contract.response.SimpleUserContent;
import com.arthurh.portfolio.socialmedia.db.model.Post;
import com.arthurh.portfolio.socialmedia.db.model.Reaction;
import com.arthurh.portfolio.socialmedia.db.model.UserContent;
import com.arthurh.portfolio.socialmedia.db.model.UserReaction;
import com.arthurh.portfolio.socialmedia.db.repository.CommentRepo;
import com.arthurh.portfolio.socialmedia.db.repository.UserReactionRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserContentMapper {
    final CommentRepo commentRepo;
    final UserReactionRepo userReactionRepo;

    public SimpleUserContent mapToSimpleContent(UserContent content) {
        return new SimpleUserContent(
                content.getId(),
                content.getUser().getUsername(),
                content.getCreatedAt(),
                extractTitleFromContent(content),
                content.getContent(),
                countReplies(content),
                countReactions(content));
    }

    @Nullable
    String extractTitleFromContent(UserContent content) {
        if (content instanceof Post post) {
            return post.getTitle();
        }
        return null;
    }

    @Transactional(readOnly = true)
    long countReplies(UserContent content) {
        return commentRepo.countByParent(content.getId());
    }

    @Transactional(readOnly = true)
    Map<String, Long> countReactions(UserContent content) {
        try (var stream = userReactionRepo.streamByContent(content.getId())) {
            return stream.map(UserReaction::getReaction)
                    .collect(groupingBy(Reaction::getName, counting()));
        }
    }
}
