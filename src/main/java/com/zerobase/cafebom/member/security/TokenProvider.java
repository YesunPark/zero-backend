package com.zerobase.cafebom.member.security;


import com.zerobase.cafebom.member.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String KEY_ROLE = "role";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간

    private final AuthService authService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    // 토큰 생성(발급)-yesun-23.08.21
    public String generateToken(Long userId, String email, Role role) {
        Claims claims = Jwts.claims() // 사용자의 정보를 저장하기 위한 claim
            .setSubject(email)
            .setId(userId + "");
        claims.put(KEY_ROLE, role);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now) // 토큰 생성 시간
            .setExpiration(expiredDate) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS512, secretKey) // 사용할 암호화 알고리즘, 시크릿 키
            .compact();
    }

    // jwt 에서 인증정보 추출-yesun-23.08.21
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authService.loadUserByUsername(getPhone(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    // 토큰에서 사용자 휴대전화번호 추출-yesun-23.08.21
    public String getPhone(String token) {
        return parseClaims(token).getSubject();
    }

    // 토큰에서 사용자 id 추출-yesun-23.08.21
    public Long getId(String token) {
        return Long.parseLong(parseClaims(token).getId());
    }

    // 토큰 유효성검사-yesun-23.08.21
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // 토큰에서 클레임 정보 추출-yesun-23.08.21
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}