package com.zerobase.reservation.configuration;

import com.zerobase.reservation.configuration.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jwt 관련 Bean 설정 클래스
 */
@Configuration
public class JwtConfiguration {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
}
