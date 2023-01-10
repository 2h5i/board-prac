package com.sparta.boardprac.user.service;

import com.sparta.boardprac.user.dto.LoginRequestDto;
import com.sparta.boardprac.user.dto.SignupRequestDto;
import com.sparta.boardprac.user.dto.TokenRequestDto;
import com.sparta.boardprac.user.dto.TokenResponseDto;
import com.sparta.boardprac.user.dto.UserLoginResponseDto;
import com.sparta.boardprac.user.dto.UserSignupResponseDto;

public interface UserService {

    UserSignupResponseDto signup(SignupRequestDto signupRequestDto);

    UserLoginResponseDto login(LoginRequestDto loginRequestDto);

    TokenResponseDto reIssue(TokenRequestDto tokenRequestDto);

}
