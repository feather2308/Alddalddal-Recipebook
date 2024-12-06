package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(String id);

    Optional<Account> findByEmail(String email);
}
