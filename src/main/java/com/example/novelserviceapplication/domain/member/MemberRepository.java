package com.example.novelserviceapplication.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    @EntityGraph(attributePaths = "authority")
    Optional<Member> findOneWithAuthoritiesById(Long id);
}
