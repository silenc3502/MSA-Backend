package com.example.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 소셜 로그인 provider 정보 (google, facebook 등)
    @Column(nullable = false)
    private String provider;

    // 해당 provider 내에서 유저 식별자 (sub, id 등)
    @Column(nullable = false)
    private String providerId;

    // AccountProfile 연결 (1:1)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private AccountProfile profile;
}