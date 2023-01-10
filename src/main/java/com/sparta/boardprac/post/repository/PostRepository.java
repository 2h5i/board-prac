package com.sparta.boardprac.post.repository;

import com.sparta.boardprac.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostByIdAndUsername(Long postId, String username);
}
