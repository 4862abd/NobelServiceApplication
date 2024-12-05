package com.example.novelserviceapplication.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    GUEST("ROLE_GUEST", "손님");


    private final String key;
    private final String title;
}
