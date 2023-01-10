package com.sparta.boardprac.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.boardprac.common.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter  extends GenericFilter {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtUtil.resolveToken((HttpServletRequest) request);

        if(token != null && jwtUtil.validateTokenExceptExpiration(token)) {
//            Authentication auth = jwtUtil.createAuthentication(token);
            Claims info = jwtUtil.getUserInfoFromToken(token);
            Authentication authentication = jwtUtil.createAuthentication(info.getSubject());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = jwtUtil.resolveToken(request);
//
//        if(token != null) {
//            if(!jwtUtil.validateToken(token)) {
//                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//            Claims info = jwtUtil.getUserInfoFromToken(token);
//            setAuthentication(info.getSubject());
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    public void setAuthentication(String username) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = jwtUtil.createAuthentication(username);
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//    }
//
//    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
//        response.setStatus(statusCode);
//        response.setContentType("application/json");
//        try {
//            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
//            response.getWriter().write(json);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
}
