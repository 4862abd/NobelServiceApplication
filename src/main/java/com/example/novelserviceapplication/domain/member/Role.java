package com.example.novelserviceapplication.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_GUEST("ROLE_GUEST", "손님");


    private final String key;
    private final String title;
}
