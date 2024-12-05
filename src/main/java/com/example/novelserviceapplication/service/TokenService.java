package com.example.novelserviceapplication.service;

import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.domain.member.token.Token;
import com.example.novelserviceapplication.domain.member.token.TokenRepository;
import com.example.novelserviceapplication.jwt.TokenProvider;
import com.example.novelserviceapplication.web.dto.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    public TokenService(TokenProvider tokenProvider, TokenRepository tokenRepository) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
    }

    public LoginResponse newToken(Authentication loginUserAuthentication, Member requestMember) {
        Date now = new Date();
        String accessToken = tokenProvider.newAccessTokenOf(loginUserAuthentication,
                tokenProvider.accessTokenExpirationDateFrom(now),
                requestMember.getId());

        Date refreshTokenExpiration = tokenProvider.refreshTokenExpirationDateFrom(now);
        String refreshToken = tokenProvider.newRefreshTokenOf(loginUserAuthentication,
                refreshTokenExpiration,
                requestMember.getId());

        tokenRepository.save(new Token.Builder()
                .member(requestMember)
                .refreshToken(refreshToken)
                .expiration(refreshTokenExpiration)
                .build());

        return new LoginResponse.Builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
