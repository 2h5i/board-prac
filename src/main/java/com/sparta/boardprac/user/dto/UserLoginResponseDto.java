package com.sparta.boardprac.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;

    public UserLoginResponseDto(Long id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
