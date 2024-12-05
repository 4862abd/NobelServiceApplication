package com.example.novelserviceapplication.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    public static final String MEMBER_ID = "memberId";
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final int MILLI_SECONDS = 1000;
    private static final String DELIMITER = ",";
    public static final String EMPTY_PASSWORD = "";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInMilliseconds,
                         @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInMilliseconds) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        createKey();
    }

    private void createKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String newAccessTokenOf(Authentication authentication, Date expiration, Long memberId) {
        return createToken(authentication, expiration, memberId);
    }

    public String newRefreshTokenOf(Authentication authentication, Date expiration, Long memberId) {
        return createToken(authentication, expiration, memberId);
    }

    public Date accessTokenExpirationDateFrom(Date now) {
        return new Date(now.getTime() + this.accessTokenValidityInMilliseconds);
    }

    public Date refreshTokenExpirationDateFrom(Date now) {
        return new Date(now.getTime() + this.refreshTokenValidityInMilliseconds);
    }

    private String createToken(Authentication authentication, Date expiration, Long memberId) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(DELIMITER));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim(MEMBER_ID, memberId)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact();
    }
}
