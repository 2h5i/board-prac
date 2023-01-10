package com.sparta.boardprac.like.service;

import com.sparta.boardprac.user.entity.User;

public interface LikeService {

    void postLike(Long postId, User user);

    void commentLike(Long commentId, User user);

    Long getPostLike(Long postId);

    Long getCommentLike(Long commentId);

    void postUnlike(Long postId, User user);

    void commentUnlike(Long commentId, User user);

}
