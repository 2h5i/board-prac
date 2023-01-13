package com.sparta.boardprac.comment.controller;

import com.sparta.boardprac.comment.dto.RequestCommentDto;
import com.sparta.boardprac.comment.service.CommentService;
import com.sparta.boardprac.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createComment(@PathVariable final Long postId,
                              @RequestBody final RequestCommentDto requestCommentDto,
                              @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        return commentService.createComment(postId, requestCommentDto, userDetails.getUser());
    }

    @PostMapping("/reply/{parentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createReplyComment(@PathVariable final Long parentId,
                                   @RequestBody final RequestCommentDto requestCommentDto,
                                   @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        return  commentService.createReplyComment(parentId, requestCommentDto, userDetails.getUser());
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCommentById(@PathVariable final Long commentId,
                              @RequestBody final RequestCommentDto requestCommentDto,
                              @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        commentService.updateCommentById(commentId, requestCommentDto, userDetails.getUser());
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommentById(@PathVariable final Long commentId,
                                  @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        commentService.deleteCommentById(commentId, userDetails.getUser());
    }

}
