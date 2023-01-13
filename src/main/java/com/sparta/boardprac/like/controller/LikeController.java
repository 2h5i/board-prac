package com.sparta.boardprac.like.controller;

import com.sparta.boardprac.common.security.UserDetailsImpl;
import com.sparta.boardprac.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void postLike(@PathVariable final Long postId, @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        likeService.postLike(postId, userDetails.getUser());
    }

    @PostMapping("/comment/{commentId}/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void commentLike(@PathVariable final Long commentId, @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        likeService.commentLike(commentId, userDetails.getUser());
    }

    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Long getPostLike(@PathVariable final Long postId) {
        return likeService.getPostLike(postId);
    }

    @GetMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Long getCommentLike(@PathVariable final Long commentId) {
        return likeService.getCommentLike(commentId);
    }

    @DeleteMapping("/post/unlike/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void postUnlike(@PathVariable final Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.postUnlike(postId, userDetails.getUser());
    }

    @DeleteMapping("/comment/unlike/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void commentUnlike(@PathVariable final Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.commentUnlike(commentId, userDetails.getUser());
    }

}
