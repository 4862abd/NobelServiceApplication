package com.example.novelserviceapplication.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignInResponse {

    private final String accessToken;
    private final String refreshToken;


}
