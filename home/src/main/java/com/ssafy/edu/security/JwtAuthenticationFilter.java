package com.ssafy.edu.security;

import com.ssafy.edu.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 헤더에서 Authorization 헤더를 가져옴
        String authorizationHeader = request.getHeader("Authorization");
        
        System.out.println("Authorization Header: " + authorizationHeader); // Authorization 헤더 확인 로그
       
        String token = null;
        String userId = null;

        // 토큰이 "Bearer "로 시작하는지 확인 후 토큰에서 사용자 ID 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userId = jwtUtil.getUserIdFromToken(token);
            System.out.println("토큰에서 추출한 사용자 ID: " + userId); // 토큰에서 추출한 사용자 ID 로그
        }

        // userId가 존재하고 SecurityContext에 인증이 없는 경우에만 인증 처리
        if (userId != null && jwtUtil.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("JWT가 유효하며 SecurityContext에 인증 정보가 없습니다. 사용자 설정 진행.");

            // UserDetails 객체를 생성해 실제 userId로 설정
            UserDetails userDetails = new User(userId, "", Collections.emptyList());
            System.out.println("userDetails: " + userDetails); // userDetails 확인 로그

            // UsernamePasswordAuthenticationToken을 생성해 SecurityContext에 저장
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("SecurityContextHolder에 인증 객체 설정됨: " + authToken); // SecurityContext 설정 확인 로그
        } else {
            System.out.println("토큰이 유효하지 않거나 이미 인증 객체가 설정되어 있음"); // 비정상 또는 이미 인증된 상태 로그
        }

        // 필터 체인에 다음 필터를 호출
        filterChain.doFilter(request, response);
    }
}
