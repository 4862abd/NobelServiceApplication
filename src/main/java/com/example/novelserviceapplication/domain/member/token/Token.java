package com.example.novelserviceapplication.domain.member.token;

import com.example.novelserviceapplication.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Date expiration;
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

//    빌더 패턴
    private Token(Builder builder) {
        this.id = builder.id;
        this.expiration = builder.expiration;
        this.refreshToken = builder.refreshToken;
        this.member = builder.member;
    }

    public static class Builder {
        private Long id;
        private Date expiration;
        private String refreshToken;
        private Member member;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder expiration(Date expiration) {
            this.expiration = expiration;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Token build() {
            return new Token(this);
        }
    }


}
