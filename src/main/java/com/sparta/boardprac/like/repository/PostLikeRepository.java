package com.sparta.boardprac.like.repository;

import com.sparta.boardprac.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Long countPostLikesByPostId(Long postId);

    Optional<PostLike> findPostLikeByPostIdAndUserId(Long postId, Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deletePostLikeByPostIdAndUserId(Long postId, Long userId);

}
