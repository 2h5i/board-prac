package com.sparta.boardprac.user.dto;

import lombok.Getter;

@Getter
public class UserSignupResponseDto {
    private Long id;
    private String username;

    public UserSignupResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
