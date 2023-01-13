package com.sparta.boardprac.user.dto;

import lombok.Getter;

@Getter
public class UserSignupResponseDto {
    private Long id;
    private String username;

    public UserSignupResponseDto(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }
}
