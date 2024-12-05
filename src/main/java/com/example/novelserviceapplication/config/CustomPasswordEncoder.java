package com.example.novelserviceapplication.config;

import org.springframework.security.crypto.password.PasswordEncoder;

// 내일 할 일
// 이거 추후 BCryptPassEncoder 로 바꿀 것임
// 암호화 관련 공부 조금만 하고
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        String registeredPassword = rawPassword.toString();

        return encodedPassword.equals(rawPassword);
    }
}
