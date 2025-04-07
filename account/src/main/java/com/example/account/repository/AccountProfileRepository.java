package com.example.account.repository;

import com.example.account.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
}
