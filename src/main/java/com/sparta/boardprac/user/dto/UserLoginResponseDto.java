package com.sparta.boardprac.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;

    public UserLoginResponseDto(final Long id, final String accessToken, final String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
