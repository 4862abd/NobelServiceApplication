package com.example.novelserviceapplication.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
public class PasswordEncoderTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void 암호_암호화_결과도출() {
        //given
        String rawPassword = "test";

        //when
        String encodedPassword1 = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);

        //then
        System.out.println("encodedPassword1: " + encodedPassword1);
        System.out.println("encodedPassword2: " + encodedPassword2);

//        assertThat(encodedPassword1).isEqualTo(encodedPassword2);

        assertThat(passwordEncoder.matches(rawPassword, encodedPassword1))
                .isEqualTo(passwordEncoder.matches(rawPassword, encodedPassword2));
    }
}
