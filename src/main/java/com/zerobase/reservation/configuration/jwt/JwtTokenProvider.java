package com.zerobase.reservation.configuration.jwt;

import com.zerobase.reservation.type.Role;
import com.zerobase.reservation.util.Aes256Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;

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

    public String getEmail(String token) {

        String encryptedEmail = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
            .getSubject();

        return Aes256Utils.decrypt(encryptedEmail);
    }


}
