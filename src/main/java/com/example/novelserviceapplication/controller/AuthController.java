package com.example.novelserviceapplication.controller;

import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.dto.ExceptionResponse;
import com.example.novelserviceapplication.exception.member.MemberNotFoundException;
import com.example.novelserviceapplication.service.MemberService;
import com.example.novelserviceapplication.service.TokenService;
import com.example.novelserviceapplication.web.dto.LoginRequest;
import com.example.novelserviceapplication.web.dto.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("${apiPrefix}/authorize")
@RestController
public class AuthController {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String SET_COOKIE_HEADER = "Set-Cookie";
    public static final String ACCESS_TOKEN_PREFIX = "Bearer ";
    public static final String REFRESH_TOKEN_PREFIX = "refresh_token=";
    public static final String REFRESH_TOKEN_SUFFIX = "; HttpOnly";

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;
    private final TokenService tokenService;


    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, MemberService memberService, TokenService tokenService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberService = memberService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Member loginMember = memberService.memberOf(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication loginUserAuthentication = newAuthentication(loginMember.getId(), loginRequest.getPassword());

        LoginResponse loginMemberToken = tokenService.newToken(loginUserAuthentication, loginMember);

        HttpHeaders httpHeaders = responseHeadersWithTokenValuesOf(
                ACCESS_TOKEN_PREFIX + loginMemberToken.getAccessToken(),
                REFRESH_TOKEN_PREFIX + loginMemberToken.getRefreshToken() + REFRESH_TOKEN_SUFFIX);

        return new ResponseEntity<>(loginMemberToken, httpHeaders, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(MemberNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    private HttpHeaders responseHeadersWithTokenValuesOf (String accessToken, String refreshToken) {
        return new HttpHeaders(new LinkedMultiValueMap<>(Map.of(
                AUTHORIZATION_HEADER, List.of(accessToken),
                SET_COOKIE_HEADER, List.of(refreshToken))
        ));
    }

    private Authentication newAuthentication(Long principal, String credential) {
        UsernamePasswordAuthenticationToken newToken =
                new UsernamePasswordAuthenticationToken(principal, credential);

        Authentication authentication = newAuthentication(newToken);

        saveAuthentication(authentication);

        return authentication;
    }

    private Authentication newAuthentication(UsernamePasswordAuthenticationToken newToken) {
        return authenticationManagerBuilder.getObject().authenticate(newToken);
    }

    private void saveAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
