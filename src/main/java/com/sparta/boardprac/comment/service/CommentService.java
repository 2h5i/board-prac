package com.sparta.boardprac.comment.service;

import com.sparta.boardprac.comment.dto.RequestCommentDto;
import com.sparta.boardprac.user.entity.User;

public interface CommentService {

    Long createComment(Long postId, RequestCommentDto requestCommentDto, User user);

    void updateCommentById(Long commentId, RequestCommentDto requestCommentDto, User user);

    void deleteCommentById(Long commentId, User user);

}
