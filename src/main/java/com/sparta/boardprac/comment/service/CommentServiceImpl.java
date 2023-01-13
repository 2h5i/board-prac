package com.sparta.boardprac.comment.service;

import com.sparta.boardprac.comment.dto.RequestCommentDto;
import com.sparta.boardprac.comment.entity.Comment;
import com.sparta.boardprac.comment.repository.CommentRepository;
import com.sparta.boardprac.like.repository.CommentLikeRepository;
import com.sparta.boardprac.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Long createReplyComment(final Long parentId, final RequestCommentDto requestCommentDto, final User user) {
        Comment parentComment = commentRepository.findById(parentId).orElseThrow(
                () -> new IllegalArgumentException("답글을 달 댓글이 없습니다.")
        );

        Comment replyComment = Comment.builder()
                .postId(parentComment.getPostId())
                .userId(user.getId())
                .content(requestCommentDto.getContent())
                .parentId(parentId)
                .build();

        return commentRepository.save(replyComment).getId();
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
        Comment comment = commentRepository.findCommentByIdAndUserId(commentId, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("삭제할 댓글이 없습니다.")
        );
        List<Comment> childrenComments = commentRepository.findCommentsByParentId(commentId);
        List<Long> childrenCommentIds = childrenComments.stream().map(Comment::getId).toList();

        commentLikeRepository.deleteCommentLikesByCommentIdIn(childrenCommentIds);
        commentLikeRepository.deleteCommentLikeByCommentId(commentId);
        commentRepository.deleteAll(childrenComments);
        commentRepository.delete(comment);
    }
}
