package com.example.novelserviceapplication.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authority {

    @Id
    @Column(name = "authority_name", length = 50)
    @Enumerated(value = EnumType.STRING)
    private Role authorityName;

}
