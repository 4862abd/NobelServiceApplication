package com.example.novelserviceapplication.config;

import com.example.novelserviceapplication.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
//                        .frameOptions(
//                                HeadersConfigurer.FrameOptionsConfig::disable
//                        ))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                new AntPathRequestMatcher("/")
                                , new AntPathRequestMatcher("/css/**")
                                , new AntPathRequestMatcher("/images/**")
                                , new AntPathRequestMatcher("/js/**")
                                , new AntPathRequestMatcher("/h2-console/**")
                                , new AntPathRequestMatcher("/api/authorize")
                        ).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/novel/**").getPattern())
                        .permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/api/**")).hasRole(Role.ROLE_ADMIN.name())
//                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return new CustomPasswordEncoder();
    }
}
