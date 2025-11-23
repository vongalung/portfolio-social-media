package com.arthurh.portfolio.socialmedia.db.service;

import com.arthurh.portfolio.socialmedia.db.model.Comment;
import com.arthurh.portfolio.socialmedia.db.model.Post;
import com.arthurh.portfolio.socialmedia.db.model.User;
import com.arthurh.portfolio.socialmedia.db.model.UserContent;
import com.arthurh.portfolio.socialmedia.db.repository.CommentRepo;
import com.arthurh.portfolio.socialmedia.db.repository.PostRepo;
import com.arthurh.portfolio.socialmedia.db.repository.UserContentRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserContentService {
    final UserContentRepo userContentRepo;
    final PostRepo postRepo;
    final CommentRepo commentRepo;

    public Page<Post> findByTypeAndUser(User user, @Nullable Pageable pageable) {
        return postRepo.findByUser_Id(user.getId(), pageable);
    }

    public Optional<UserContent> findById(UUID id) {
        return userContentRepo.findById(id);
    }

    public Page<Comment> findRepliesById(UUID id, @Nullable Pageable pageable) {
        return commentRepo.findByParentId(id, pageable);
    }

    public UserContent save(UserContent content) {
        return userContentRepo.save(content);
    }

    public Optional<UserContent> findByIdForUpdate(UUID id) {
        return userContentRepo.findByIdForUpdate(id);
    }
}
