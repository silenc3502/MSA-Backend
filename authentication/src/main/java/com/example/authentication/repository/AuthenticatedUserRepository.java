package com.example.authentication.repository;

import com.example.authentication.entity.AuthenticatedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Long> {
    Optional<AuthenticatedUser> findByOauthId(String oauthId);
}
