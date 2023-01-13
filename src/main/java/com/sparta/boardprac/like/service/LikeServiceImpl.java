package com.sparta.boardprac.like.service;

import com.sparta.boardprac.like.entity.CommentLike;
import com.sparta.boardprac.like.entity.PostLike;
import com.sparta.boardprac.like.repository.CommentLikeRepository;
import com.sparta.boardprac.like.repository.PostLikeRepository;
import com.sparta.boardprac.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    @Override
    public void postLike(final Long postId, final User user) {

        if(postLikeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        PostLike postLike = PostLike.builder()
                .postId(postId)
                .userId(user.getId())
                .build();

        postLikeRepository.save(postLike);
    }

    @Transactional
    @Override
    public void commentLike(final Long commentId, final User user) {

        if(commentLikeRepository.existsByCommentIdAndUserId(commentId, user.getId())) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        CommentLike commentLike = CommentLike.builder()
                .commentId(commentId)
                .userId(user.getId())
                .build();

        commentLikeRepository.save(commentLike);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getPostLike(final Long postId) {
        return postLikeRepository.countPostLikesByPostId(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getCommentLike(final Long commentId) {
        return commentLikeRepository.countCommentLikesByCommentId(commentId);
    }

    @Transactional
    @Override
    public void postUnlike(final Long postId, final User user) {
        PostLike postLike = postLikeRepository.findPostLikeByPostIdAndUserId(postId, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 좋아요가 없습니다.")
        );

        postLikeRepository.delete(postLike);
    }

    @Transactional
    @Override
    public void commentUnlike(final Long commentId, final User user) {
        CommentLike commentLike = commentLikeRepository.findCommentLikeByCommentIdAndUserId(commentId, user.getId())
                .orElseThrow(
                        () -> new IllegalArgumentException("해당하는 좋아요가 없습니다.")
                );

        commentLikeRepository.delete(commentLike);
    }
}
