package com.sparta.boardprac.post.service;

import com.sparta.boardprac.comment.entity.Comment;
import com.sparta.boardprac.comment.repository.CommentRepository;
import com.sparta.boardprac.post.dto.RequestPostDto;
import com.sparta.boardprac.post.dto.ResponsePosWithCommentstDto;
import com.sparta.boardprac.post.dto.ResponsePostDto;
import com.sparta.boardprac.post.entity.Post;
import com.sparta.boardprac.post.repository.PostRepository;
import com.sparta.boardprac.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public Long createPost(RequestPostDto requestPostDto, User user) {
        Post post = Post.builder()
                .title(requestPostDto.getTitle())
                .content(requestPostDto.getContent())
                .username(user.getUsername())
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    @Override
    public ResponsePosWithCommentstDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        List<Comment> comments = commentRepository.findCommentsByPostIdOrderByCreatedAtDesc(postId);

        return ResponsePosWithCommentstDto.builder()
                .post(post)
                .comments(comments)
                .build();
    }

    @Transactional
    @Override
    public Page<ResponsePostDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return ResponsePostDto.of(posts);
    }

    @Transactional
    @Override
    public void updatePostById(Long postId, RequestPostDto requestPostDto, User user) {
        Post post = postRepository.findPostByIdAndUsername(postId, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("수정할 게시글이 존재하지 않거나 권한이 없습니다.")
        );
        post.update(requestPostDto.getTitle(), requestPostDto.getContent());
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void updatePostByIdByAdmin(Long postId, RequestPostDto requestPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("수정할 게시글이 존재하지 않습니다.")
        );
        post.update(requestPostDto.getTitle(), requestPostDto.getContent());
        postRepository.saveAndFlush(post);
    }

    @Transactional
    @Override
    public void deletePostById(Long postId, User user) {
        Post post = postRepository.findPostByIdAndUsername(postId, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.")
        );
        postRepository.delete(post);
        commentRepository.deleteCommentsByPostId(postId);
    }

    @Transactional
    @Override
    public void deletePostByIdByAdmin(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.")
        );
        postRepository.delete(post);
        commentRepository.deleteCommentsByPostId(postId);
    }

}