package com.example.novelserviceapplication.domain.member.loginHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    Optional<LoginHistory> findByMemberId(Long memberId);
}
