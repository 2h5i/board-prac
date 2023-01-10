package com.sparta.boardprac.post.controller;

import com.sparta.boardprac.common.core.PageWrapper;
import com.sparta.boardprac.common.security.UserDetailsImpl;
import com.sparta.boardprac.post.dto.RequestPostDto;
import com.sparta.boardprac.post.dto.ResponsePosWithCommentstDto;
import com.sparta.boardprac.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody @Valid final RequestPostDto requestPostDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(requestPostDto, userDetails.getUser());
    }

    @GetMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponsePosWithCommentstDto getPostById(@PathVariable final Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public PageWrapper getPosts(@RequestParam(value = "page", defaultValue = "0", required = false) final int page,
                                @RequestParam(value = "size", defaultValue = "10", required = false) final int size) {
        return PageWrapper.of(postService.getPosts(PageRequest.of(page, size)));
    }

    @PutMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePostById(@PathVariable final Long postId,
                               @RequestBody final RequestPostDto requestPostDto,
                               @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        postService.updatePostById(postId, requestPostDto, userDetails.getUser());
    }

    @PutMapping("/admin/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updatePostByIdByAdmin(@PathVariable final Long postId,
                                      @RequestBody final RequestPostDto requestPostDto) {
        postService.updatePostByIdByAdmin(postId, requestPostDto);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostById(@PathVariable final Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePostById(postId, userDetails.getUser());
    }

    @DeleteMapping("/admin/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePostById(@PathVariable final Long postId) {
        postService.deletePostByIdByAdmin(postId);
    }

}
