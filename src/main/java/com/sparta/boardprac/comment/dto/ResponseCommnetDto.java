package com.sparta.boardprac.comment.dto;

import com.sparta.boardprac.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ResponseCommnetDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;

    public ResponseCommnetDto(final Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.userId = comment.getUserId();
    }

    public static ResponseCommnetDto of(final Comment comment) {
        return new ResponseCommnetDto(comment);
    }

    public static List<ResponseCommnetDto> of(final List<Comment> comments) {
        return comments.stream().map(ResponseCommnetDto::of).collect(Collectors.toList());
    }
}
