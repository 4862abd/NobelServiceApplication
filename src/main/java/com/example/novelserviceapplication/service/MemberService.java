package com.example.novelserviceapplication.service;

import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.domain.member.MemberRepository;
import com.example.novelserviceapplication.exception.member.MemberNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member memberOf(String username, String password) {
        Member loginMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new MemberNotFoundException("이름에 해당하는 멤버정보가 없습니다: " + username);
                });

//        if (!passwordEncoder.matches(password, loginMember.getPassword())) {
//            throw new IllegalArgumentException("입력하신 ID와 패스워드에 해당하는 정보가 없습니다. 로그인 정보를 확인해주세요.");
//        }
        if (!password.equals(loginMember.getPassword())) {
            throw new MemberNotFoundException("입력하신 ID와 패스워드에 해당하는 정보가 없습니다. 로그인 정보를 확인해주세요.");
        }

        return loginMember;
    }
}
