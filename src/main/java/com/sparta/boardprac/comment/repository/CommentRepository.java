package com.sparta.boardprac.comment.repository;

import com.sparta.boardprac.comment.entity.Comment;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByPostId(@NonNull Long postId);

    List<Comment> findCommentsByPostIdOrderByCreatedAtDesc(Long postId);

    Optional<Comment> findCommentByIdAndUserId(Long commentId, Long userId);

    void deleteCommentByIdAndUserId(Long commentId, Long userId);

    List<Comment> findCommentsByPostId(Long postId);
}
