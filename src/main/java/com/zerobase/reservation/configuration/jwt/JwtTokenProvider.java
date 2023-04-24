package com.zerobase.reservation.configuration.jwt;

import com.zerobase.reservation.dto.User;
import com.zerobase.reservation.type.Role;
import com.zerobase.reservation.util.Aes256Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;

/**
 * JWT 토큰 관련 클래스
 *
 * 1) 토큰 생성
 * 2) User 반환(id, email, role)
 *
 */
public class JwtTokenProvider {

    private String secretKey = "secretKeysecretKeysecretKeysecretKeysecretKeysecretKey";
    private final Long tokenValidMillisecond = 1000L * 60 * 60;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String email, Long id, Role role) {

        Claims claims = Jwts.claims().setSubject(Aes256Utils.encrypt(email))
                .setId(Aes256Utils.encrypt(id.toString()));
        claims.put("role", role);

        Date now = new Date();

        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();

        return token;
    }

    public User getUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return User.builder()
            .id(Long.parseLong(Aes256Utils.decrypt(claims.getId())))
            .email( Aes256Utils.decrypt(claims.getSubject()))
            .role(Role.valueOf((String) claims.get("role")))
            .build();

    }

}
