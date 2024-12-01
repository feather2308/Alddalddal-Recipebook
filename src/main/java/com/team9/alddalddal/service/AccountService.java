package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public void add(String id, String password,
                    String name, String nickname, String email) {
        Account account = new Account();

        account.setId(id);
        account.setPw(password);
        account.setName(name);
        account.setNickname(nickname);
        account.setEmail(email);

        accountRepository.save(account);
    }

    public boolean authenticate(String id, String password) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            Account account_ = account.get();
            return account_.getPw().equals(password);
        } else {
            return false;
        }
    }
}