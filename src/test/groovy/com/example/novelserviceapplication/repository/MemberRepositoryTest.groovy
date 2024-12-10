package com.example.novelserviceapplication.repository

import com.example.novelserviceapplication.domain.MemberTest
import com.example.novelserviceapplication.domain.member.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@DataJpaTest
class MemberRepositoryTest extends Specification {

    @Autowired
    MemberRepository memberRepository

    PasswordEncoder passwordEncoder

//    def setup() {
//        passwordEncoder = new BCryptPasswordEncoder()
//        memberRepository.save(MemberTest.TEST_ADMIN_MEMBER);
//    }
//
//    def cleanup() {
//        memberRepository.deleteAll()
//    }

    def "멤버 정보를 매개변수로 멤버 정보를 반환한다" () {
        given:
        def username = "test"
        def password = "test"

        when:
        def member = memberRepository.findByUsername(username)
        def passwordMatched = passwordEncoder.matches(password, member.get().password)

        then:
        verifyAll {
            member.present == true
            passwordMatched == true
        }
    }
}
