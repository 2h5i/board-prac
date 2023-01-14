package com.sparta.boardprac.post.service;

import com.sparta.boardprac.comment.entity.Comment;
import com.sparta.boardprac.comment.repository.CommentRepository;
import com.sparta.boardprac.like.repository.CommentLikeRepository;
import com.sparta.boardprac.like.repository.PostLikeRepository;
import com.sparta.boardprac.post.dto.RequestPostDto;
import com.sparta.boardprac.post.dto.ResponsePostDto;
import com.sparta.boardprac.post.dto.ResponsePostWithCommentsDto;
import com.sparta.boardprac.post.entity.Post;
import com.sparta.boardprac.post.repository.PostRepository;
import com.sparta.boardprac.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    @Override
    public Long createPost(final RequestPostDto requestPostDto, final User user) {
        Post post = Post.builder()
                .title(requestPostDto.getTitle())
                .content(requestPostDto.getContent())
                .username(user.getUsername())
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    @Override
    public ResponsePostWithCommentsDto getPostById(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시물입니다.")
        );
        List<Comment> comments = commentRepository.findCommentsByPostIdOrderByCreatedAtDesc(postId);
        Long likeCount = postLikeRepository.countPostLikesByPostId(post.getId());

        return ResponsePostWithCommentsDto.builder()
                .post(post)
                .likeCount(likeCount)
                .comments(comments)
                .build();
    }

    @Transactional
    @Override
    public Page<ResponsePostDto> getPosts(final Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        List<ResponsePostDto> newContent = new ArrayList<>();

        for (Post post : posts.getContent()) {
            Long likeCount = postLikeRepository.countPostLikesByPostId(post.getId());
            newContent.add(ResponsePostDto.of(post, likeCount));
        }

        return new PageImpl<>(newContent, pageable, posts.getTotalElements());
    }

    @Transactional
    @Override
    public void updatePostById(final Long postId, final RequestPostDto requestPostDto, final User user) {
        Post post = postRepository.findPostByIdAndUsername(postId, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("수정할 게시글이 존재하지 않거나 권한이 없습니다.")
        );
        post.update(requestPostDto.getTitle(), requestPostDto.getContent());
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void updatePostByIdByAdmin(final Long postId, final RequestPostDto requestPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("수정할 게시글이 존재하지 않습니다.")
        );
        post.update(requestPostDto.getTitle(), requestPostDto.getContent());
        postRepository.saveAndFlush(post);
    }

    @Transactional
    @Override
    public void deletePostById(final Long postId, final User user) {
        Post post = postRepository.findPostByIdAndUsername(postId, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.")
        );

        deletePost(post);
    }

    @Transactional
    @Override
    public void deletePostByIdByAdmin(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.")
        );

        deletePost(post);
    }

    private void deletePost(final Post post) {
        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());
        List<Long> commentIds = comments.stream().map(Comment::getId).toList();

        commentLikeRepository.deleteCommentLikesByCommentIdIn(commentIds);
        commentRepository.deleteCommentsByPostId(post.getId());
        postLikeRepository.deletePostLikesByPostId(post.getId());
        postRepository.delete(post);
    }

}
