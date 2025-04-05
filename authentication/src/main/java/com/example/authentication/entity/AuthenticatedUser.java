package com.example.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"oauthId", "provider"})
})
public class AuthenticatedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;
    private String email;
    private String provider;

    public AuthenticatedUser(String oauthId, String email, String provider) {
        this.oauthId = oauthId;
        this.email = email;
        this.provider = provider;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getEmail() {
        return email;
    }

    public String getProvider() {
        return provider;
    }
}

