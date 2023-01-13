package com.sparta.boardprac.like.repository;

import com.sparta.boardprac.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Long countCommentLikesByCommentId(Long commentId);

    Optional<CommentLike> findCommentLikeByCommentIdAndUserId(Long commentId, Long userId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    void deleteCommentLikeByCommentId(Long commentId);

    void deleteCommentLikesByCommentIdIn(List<Long> commentIds);

}
