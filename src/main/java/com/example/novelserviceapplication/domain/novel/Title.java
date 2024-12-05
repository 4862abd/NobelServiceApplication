package com.example.novelserviceapplication.domain.novel;

import com.example.novelserviceapplication.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

}
