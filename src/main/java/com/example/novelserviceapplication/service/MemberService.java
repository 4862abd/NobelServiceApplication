package com.example.novelserviceapplication.service;

import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.domain.member.MemberRepository;
import com.example.novelserviceapplication.domain.member.loginHistory.LoginHistory;
import com.example.novelserviceapplication.domain.member.loginHistory.LoginHistoryRepository;
import com.example.novelserviceapplication.exception.member.MemberLockedException;
import com.example.novelserviceapplication.exception.member.MemberNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, LoginHistoryRepository loginHistoryRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.loginHistoryRepository = loginHistoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member memberOf(String username, String password) {
        Member loginMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("이름에 해당하는 멤버정보가 없습니다: " + username));

        if (!passwordEncoder.matches(password, loginMember.getPassword())) {
//            내일 할 일
//            로그인 성공, 실패 - 추후 구현
//            if (loginMember.isLocked()) {
//                throw new MemberLockedException("입력하신 계정은 현재 잠김 상태 입니다. 이용을 원할시 문의해주십시오.");
//            }

            throw new MemberNotFoundException("입력하신 ID와 패스워드에 해당하는 정보가 없습니다. 로그인 정보를 확인해주세요.", new Throwable(loginMember.getId().toString()));
        }

        return loginMember;
    }

    public Member memberOf(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당되는 계정 정보가 없습니다."));
    }

    public void loginSuccess(Long memberId) {
        Member loginMember = memberOf(memberId);

        if (loginMember.isFirstLogin()) {
            loginMember.firstLogin();
        }

        loginMember.getLoginHistory().loginSuccess();
    }

    public void loginFailed(Long memberId) {
        Member loginMember = memberOf(memberId);

        if (loginMember.isFirstLogin()) {
            loginMember.firstLogin();
        }

        loginMember.getLoginHistory().loginFailed();
    }
}
