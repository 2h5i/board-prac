package com.sparta.boardprac.comment.service;

import com.sparta.boardprac.comment.dto.RequestCommentDto;
import com.sparta.boardprac.comment.entity.Comment;
import com.sparta.boardprac.comment.repository.CommentRepository;
import com.sparta.boardprac.like.repository.CommentLikeRepository;
import com.sparta.boardprac.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    @Override
    public Long createComment(final Long postId, final RequestCommentDto requestCommentDto, final User user) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(user.getId())
                .content(requestCommentDto.getContent())
                .build();

        return commentRepository.save(comment).getId();
    }

    @Transactional
    @Override
    public void updateCommentById(final Long commentId, final RequestCommentDto requestCommentDto, final User user) {
        Comment comment = commentRepository.findCommentByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        comment.update(requestCommentDto.getContent());
        commentRepository.saveAndFlush(comment);
    }

    @Transactional
    @Override
    public void deleteCommentById(final Long commentId, final User user) {
        commentLikeRepository.deleteCommentLikeByCommentIdAndUserId(commentId, user.getId());
        commentRepository.deleteCommentByIdAndUserId(commentId, user.getId());
    }
}
