package com.example.novelserviceapplication.domain.member.loginHistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class LoginHistory {

    @Transient
    private final int LIMIT_COUNT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    @Enumerated(value = EnumType.STRING)
    private LockYn lockYn;
    private int tryCount;
    private Date tryDate;

    public LoginHistory(Long memberId) {
        this.memberId = memberId;
    }

    public void loginSuccess() {
        initTryCount();
        initLockYn();
    }

    public void loginFailed() {
        increaseTryCount();

        if (isTryCountLimited()) {
            lock();
        }
    }

    public boolean isLockY() {
        return (this.lockYn == LockYn.Y);
    }

    public void initTryCount() {
        this.tryCount = 0;
    }
    public void initLockYn() {
        this.lockYn = LockYn.N;
    }

    public void increaseTryCount() {
        this.tryCount++;
    }

    public boolean isTryCountLimited() {
        return this.tryCount == LIMIT_COUNT;
    }

    public void lock() {
        this.lockYn = LockYn.Y;
    }
}
