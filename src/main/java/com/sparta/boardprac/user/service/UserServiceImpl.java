package com.sparta.boardprac.user.service;

import com.sparta.boardprac.common.jwt.JwtUtil;
import com.sparta.boardprac.user.dto.LoginRequestDto;
import com.sparta.boardprac.user.dto.SignupRequestDto;
import com.sparta.boardprac.user.dto.TokenRequestDto;
import com.sparta.boardprac.user.dto.TokenResponseDto;
import com.sparta.boardprac.user.dto.UserLoginResponseDto;
import com.sparta.boardprac.user.dto.UserSignupResponseDto;
import com.sparta.boardprac.user.entity.User;
import com.sparta.boardprac.user.entity.UserRole;
import com.sparta.boardprac.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ADMIN TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    @Override
    public UserSignupResponseDto signup(final SignupRequestDto signupRequestDto) {
        validateDuplicated(signupRequestDto.getUsername());

        // 사용자 ROLE 확인
        UserRole userRole = UserRole.USER;
        if(signupRequestDto.isAdmin()) {
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            userRole = UserRole.ADMIN;
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .email(signupRequestDto.getEmail())
                .userRole(userRole)
                .build();

        userRepository.save(user);
        return new UserSignupResponseDto(user.getId(), user.getUsername());
    }

    @Transactional(readOnly = true)
    @Override
    public UserLoginResponseDto login(final LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user =  userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.updateRefreshToken(jwtUtil.createRefreshToken());
        userRepository.saveAndFlush(user);

        return new UserLoginResponseDto(user.getId(),
                jwtUtil.createToken(user.getUsername(), user.getUserRole()),
                user.getRefreshToken());
    }

    @Transactional
    @Override
    public TokenResponseDto reIssue(final TokenRequestDto tokenRequestDto) {
        if(!jwtUtil.validateTokenExceptExpiration(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        User user = findUserByToken(tokenRequestDto);

        if(!user.getRefreshToken().equals(tokenRequestDto.getRefreshToken())){
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        String accessToken = jwtUtil.createToken(user.getUsername(), user.getUserRole());
        String refreshToken = jwtUtil.createRefreshToken();

        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    // 중복 사용자 확인
    public void validateDuplicated(final String username) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용자가 존재합니다.");
        }
    }

    private User findUserByToken(final TokenRequestDto tokenRequestDto) {
        Claims claims = jwtUtil.getUserInfoFromToken(tokenRequestDto.getAccessToken().substring(7));
        String username = claims.getSubject();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );
    }
}
