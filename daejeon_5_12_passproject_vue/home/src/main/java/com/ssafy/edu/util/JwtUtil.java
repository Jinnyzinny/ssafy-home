package com.ssafy.edu.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 키 생성
    private static final long EXPIRATION_TIME = 86400000; // 1일

    // 사용자 ID와 이름을 포함한 JWT 토큰 생성
    public String generateToken(String userId, String userName) {
        return Jwts.builder()
                .setSubject(userId) // 사용자 ID를 subject로 설정
                .claim("userName", userName) // 사용자 이름을 클레임으로 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // JWT 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
    	Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    // JWT 토큰에서 사용자 이름 추출
    public String getUserNameFromToken(String token) {
        return getClaims(token).get("userName", String.class);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("토큰 검증 실패: " + e.getMessage()); // 토큰 검증 오류 로그 추가
            return false;
        }
    }

    // 클레임 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
