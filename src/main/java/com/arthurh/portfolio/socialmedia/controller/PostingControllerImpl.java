package com.arthurh.portfolio.socialmedia.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.arthurh.portfolio.socialmedia.contract.controller.PostingController;
import com.arthurh.portfolio.socialmedia.contract.request.NewPost;
import com.arthurh.portfolio.socialmedia.contract.request.NewReply;
import com.arthurh.portfolio.socialmedia.contract.response.SimpleUserContent;
import com.arthurh.portfolio.socialmedia.service.PostingControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PostingControllerImpl implements PostingController {
    final PostingControllerService postingControllerService;

    @Override
    public Page<SimpleUserContent> getPosts(Pageable pageable) {
        return postingControllerService.getPosts(pageable);
    }

    @Override
    public ResponseEntity<SimpleUserContent> createPost(NewPost request) {
        var response = postingControllerService.createPost(request);
        return new ResponseEntity<>(response, CREATED);
    }

    @Override
    public SimpleUserContent getContent(UUID id) {
        return postingControllerService.getContent(id);
    }

    @Override
    public ResponseEntity<SimpleUserContent> addReply(UUID id, NewReply request) {
        var response = postingControllerService.addReply(id, request);
        return new ResponseEntity<>(response, CREATED);
    }

    @Override
    public Page<SimpleUserContent> getReplies(UUID id, Pageable pageable) {
        return postingControllerService.getReplies(id, pageable);
    }

    @Override
    public Page<String> reactions(Pageable pageable) {
        return postingControllerService.reactions(pageable);
    }

    @Override
    public SimpleUserContent addReaction(UUID id, String reaction) {
        return postingControllerService.addReaction(id, reaction);
    }

    @Override
    public SimpleUserContent removeReaction(UUID id) {
        return postingControllerService.removeReaction(id);
    }
}
