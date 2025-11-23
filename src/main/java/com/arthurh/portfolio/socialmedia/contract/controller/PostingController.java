package com.arthurh.portfolio.socialmedia.contract.controller;

import com.arthurh.portfolio.socialmedia.contract.request.NewPost;
import com.arthurh.portfolio.socialmedia.contract.request.NewReply;
import com.arthurh.portfolio.socialmedia.contract.response.SimpleUserContent;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequestMapping("/post")
public interface PostingController {
    @GetMapping
    Page<SimpleUserContent> getPosts(Pageable pageable);

    @GetMapping("/self")
    Page<SimpleUserContent> getSelfPosts(Pageable pageable);

    @PostMapping
    ResponseEntity<SimpleUserContent> createPost(@RequestBody NewPost request);

    @GetMapping("/{id}")
    SimpleUserContent getContent(@PathVariable UUID id);

    @PostMapping("/{id}")
    ResponseEntity<SimpleUserContent> addReply(@PathVariable UUID id,
                                               @RequestBody NewReply request);

    @GetMapping("/{id}/replies")
    Page<SimpleUserContent> getReplies(@PathVariable UUID id,
                                       Pageable pageable);

    @GetMapping("/reactions")
    Page<String> reactions(Pageable pageable);

    @PostMapping("/{id}/reaction")
    SimpleUserContent addReaction(@PathVariable UUID id,
                                  @RequestParam @NotBlank String reaction);

    @DeleteMapping("/{id}/reaction")
    SimpleUserContent removeReaction(@PathVariable UUID id);
}
