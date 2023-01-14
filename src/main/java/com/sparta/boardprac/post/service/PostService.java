package com.sparta.boardprac.post.service;

import com.sparta.boardprac.post.dto.RequestPostDto;
import com.sparta.boardprac.post.dto.ResponsePostDto;
import com.sparta.boardprac.post.dto.ResponsePostWithCommentsDto;
import com.sparta.boardprac.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long createPost(RequestPostDto requestPostDto, User user);

    ResponsePostWithCommentsDto getPostById(Long postId);

    Page<ResponsePostDto> getPosts(Pageable pageable);

    void updatePostById(Long postId, RequestPostDto requestPostDto, User user);

    void updatePostByIdByAdmin(Long postId, RequestPostDto requestPostDto);

    void deletePostById(Long postId, User user);

    void deletePostByIdByAdmin(Long postId);

}
