package com.example.novelserviceapplication.controller;

import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.dto.ExceptionResponse;
import com.example.novelserviceapplication.exception.member.MemberNotFoundException;
import com.example.novelserviceapplication.service.MemberService;
import com.example.novelserviceapplication.service.TokenService;
import com.example.novelserviceapplication.web.dto.LoginRequest;
import com.example.novelserviceapplication.web.dto.LoginResponse;
import jakarta.transaction.Transactional;
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

        // 원래는 username 을 이용해서 principal 로 이용하는 것이 보통이다.
        // 하지만 Member 의 id 로 Long 값을 이용하며
        // 기본 UserDetailsManager 인 InMemoryUserDetailsManager 를 이용하지 않고
        // UserDetailsService 를 상속한 Class 를 구현하여 loadUserByUsername 를 오버라이딩 했다.
        // 왜 그랬을까?
        // 키 값을 이용하려고 한다고 하기에는 이후 로직에서도 조회된 Member 변수를 사용하고 있다.
        // 업무적으로 한 것 뿐일까?
        Authentication loginUserAuthentication = newAuthentication(loginMember.getId(), loginRequest.getPassword());

        LoginResponse loginMemberToken = tokenService.newToken(loginUserAuthentication, loginMember);

        HttpHeaders httpHeaders = responseHeadersWithTokenValuesOf(
                ACCESS_TOKEN_PREFIX + loginMemberToken.getAccessToken(),
                REFRESH_TOKEN_PREFIX + loginMemberToken.getRefreshToken() + REFRESH_TOKEN_SUFFIX);
//        내일 할 일
//        로그인 성공, 실패 - 추후 구현
//        memberService.loginSuccess(loginMember.getId());

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
        // 기본 로직 (로그인 시도한 유저의 정보가 일치하여 조회된 상태, 재 확인):
        //
        // ProviderManager.authenticate(Authentication authentication) ->
        // AbstractUserDetailsAuthenticationProvider.authenticate(Authentication authentication) ->
        // DaoAuthenticationProvider.retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) ->
        // InMemoryUserDetailsManager.loadUserByUsername(String username) -> 그 양반은 이 메서드가 재정의한 UserDetailsService 의 loadUserByUsername 를 재정의한 것임.
        // HashMap.get(Object key) 까지만 팠다.
        // 즉, 이 로직은 이미 내가 조회하여 메모리에 들어가 있어야 동작? 하는 것으로 보인다.
        return authenticationManagerBuilder.getObject().authenticate(newToken);
    }

    private void saveAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
