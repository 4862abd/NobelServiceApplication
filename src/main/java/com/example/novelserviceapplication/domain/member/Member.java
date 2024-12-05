package com.example.novelserviceapplication.domain.member;

import com.example.novelserviceapplication.domain.member.loginHistory.LockYn;
import com.example.novelserviceapplication.domain.member.loginHistory.LoginHistory;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String initDate;
    private String nickname;

    @OneToOne
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Authority authority;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "member_id")
    private LoginHistory loginHistory;

    public void firstLogin() {
        this.loginHistory = new LoginHistory(id);
    }

    public boolean isFirstLogin() {
        return (this.loginHistory == null);
    }

    public boolean isLocked() {
        return loginHistory.isLockY();
    }
}
