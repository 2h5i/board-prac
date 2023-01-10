package com.sparta.boardprac.user.controller;

import com.sparta.boardprac.user.dto.LoginRequestDto;
import com.sparta.boardprac.user.dto.SignupRequestDto;
import com.sparta.boardprac.user.dto.TokenRequestDto;
import com.sparta.boardprac.user.dto.TokenResponseDto;
import com.sparta.boardprac.user.dto.UserLoginResponseDto;
import com.sparta.boardprac.user.dto.UserSignupResponseDto;
import com.sparta.boardprac.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserSignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("/reissue")
    public TokenResponseDto reIssue(@RequestBody TokenRequestDto tokenRequestDto) {
        return userService.reIssue(tokenRequestDto);
    }

}
