package com.sparta.boardprac.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RequestPostDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
