package com.example.novelserviceapplication.service;

import com.example.novelserviceapplication.domain.member.Authority;
import com.example.novelserviceapplication.domain.member.Member;
import com.example.novelserviceapplication.domain.member.MemberRepository;
import com.example.novelserviceapplication.exception.member.MemberNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return memberRepository.findOneWithAuthoritiesById(Long.valueOf(username))
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createUser(Member member) {
        List<GrantedAuthority> grantedAuthorities = List.of(member.getAuthority()).stream()
                .map(Authority::getAuthorityName)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(member.getId().toString(), member.getPassword(), grantedAuthorities);
    }
}
