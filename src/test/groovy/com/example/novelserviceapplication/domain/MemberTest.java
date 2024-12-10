package com.example.novelserviceapplication.domain;

import com.example.novelserviceapplication.domain.member.Authority;
import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.domain.member.Role;

import java.util.Date;

public class MemberTest {
    public static final Member TEST_ADMIN_MEMBER = new Member(1L, "test", "test@test.com", "test", new Date(System.currentTimeMillis()), "테스터", new Authority(Role.ROLE_ADMIN), null);
}
