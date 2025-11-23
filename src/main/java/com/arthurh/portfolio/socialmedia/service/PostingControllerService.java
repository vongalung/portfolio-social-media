package com.arthurh.portfolio.socialmedia.service;

import static org.springframework.http.HttpStatus.*;

import com.arthurh.portfolio.socialmedia.contract.request.NewPost;
import com.arthurh.portfolio.socialmedia.contract.request.NewReply;
import com.arthurh.portfolio.socialmedia.contract.response.SimpleUserContent;
import com.arthurh.portfolio.socialmedia.db.model.Comment;
import com.arthurh.portfolio.socialmedia.db.model.Post;
import com.arthurh.portfolio.socialmedia.db.model.Reaction;
import com.arthurh.portfolio.socialmedia.db.model.UserReaction;
import com.arthurh.portfolio.socialmedia.db.service.ReactionService;
import com.arthurh.portfolio.socialmedia.db.service.UserContentService;
import com.arthurh.portfolio.socialmedia.db.service.UserReactionService;
import jakarta.persistence.LockTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostingControllerService {
    final ControllerHelperService controllerHelperService;
    final UserContentService userContentService;
    final UserReactionService userReactionService;
    final ReactionService reactionService;
    final UserContentMapper mapper;

    @Transactional
    public Page<SimpleUserContent> getAllPosts(Pageable pageable) {
        return userContentService.findAllPosts(pageable)
                .map(mapper::mapToSimpleContent);
    }

    @Transactional
    public Page<SimpleUserContent> getSelfPosts(Pageable pageable) {
        var user = controllerHelperService.findAndAuthorizeUser();
        return userContentService.findPostsByUser(user, pageable)
                .map(mapper::mapToSimpleContent);
    }

    @Transactional
    public SimpleUserContent createPost(NewPost request) {
        var user = controllerHelperService.findAndAuthorizeUser();

        var post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setUser(user);
        return mapper.mapToSimpleContent(userContentService.save(post));
    }

    @Transactional(readOnly = true)
    public SimpleUserContent getContent(UUID id) {
        return userContentService.findById(id)
                .map(mapper::mapToSimpleContent)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Transactional
    public SimpleUserContent addReply(UUID id, NewReply request) {
        var user = controllerHelperService.findAndAuthorizeUser();
        var content = userContentService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        var comment = new Comment();
        comment.setContent(request.content());
        comment.setUser(user);
        comment.setParent(content);
        return mapper.mapToSimpleContent(userContentService.save(comment));
    }

    @Transactional(readOnly = true)
    public Page<SimpleUserContent> getReplies(UUID id, Pageable pageable) {
        return userContentService.findRepliesById(id, pageable)
                .map(mapper::mapToSimpleContent);
    }

    @Transactional(readOnly = true)
    public Page<String> reactions(Pageable pageable) {
        return reactionService.findAll(pageable).map(Reaction::getName);
    }

    @Transactional
    public SimpleUserContent addReaction(UUID id, String reactionName) {
        var user = controllerHelperService.findAndAuthorizeUser();
        var reaction = reactionService.findByName(reactionName)
                .orElseThrow(() -> new ResponseStatusException(
                        BAD_REQUEST, "Invalid reaction"));
        try {
            var content = userContentService.findByIdForUpdate(id)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
            var userReaction = userReactionService.findByContentAndUser(id, user)
                    .orElse(null);
            if (userReaction != null && !reaction.equals(userReaction.getReaction())) {
                content.removeReaction(userReaction);
                userReaction = null;
            }
            if (userReaction == null) {
                userReaction = UserReaction.builder()
                        .reaction(reaction)
                        .user(user)
                        .build();
                content.addReaction(userReaction);
                content = userContentService.save(content);
            }
            return mapper.mapToSimpleContent(content);
        } catch (LockTimeoutException e) {
            throw new ResponseStatusException(
                    GATEWAY_TIMEOUT, GATEWAY_TIMEOUT.getReasonPhrase(), e);
        }
    }

    @Transactional
    public SimpleUserContent removeReaction(UUID id) {
        var user = controllerHelperService.findAndAuthorizeUser();
        try {
            var content = userContentService.findByIdForUpdate(id)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
            var userReaction = userReactionService.findByContentAndUser(id, user)
                    .orElse(null);
            if (userReaction == null) {
                return mapper.mapToSimpleContent(content);
            }

            content.removeReaction(userReaction);
            return mapper.mapToSimpleContent(userContentService.save(content));
        } catch (LockTimeoutException e) {
            throw new ResponseStatusException(
                    GATEWAY_TIMEOUT, GATEWAY_TIMEOUT.getReasonPhrase(), e);
        }
    }
}
