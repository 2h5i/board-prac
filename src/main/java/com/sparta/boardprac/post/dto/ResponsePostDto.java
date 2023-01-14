package com.sparta.boardprac.post.dto;

import com.sparta.boardprac.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponsePostDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private ResponsePostDto(final Post post, final Long likeCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.likeCount = likeCount;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public static ResponsePostDto of(final Post post,
                                     final Long likeCount) {
        return ResponsePostDto.builder().post(post).likeCount(likeCount).build();
    }
}
