package com.sparta.boardprac.post.dto;

import com.sparta.boardprac.comment.dto.ResponseCommnetDto;
import com.sparta.boardprac.comment.entity.Comment;
import com.sparta.boardprac.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ResponsePostWithCommentsDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private Long likeCount;
    private List<ResponseCommnetDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public ResponsePostWithCommentsDto(final Post post, final List<Comment> comments,final Long likeCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.likeCount = likeCount;
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.comments = ResponseCommnetDto.of(comments);
    }

}
