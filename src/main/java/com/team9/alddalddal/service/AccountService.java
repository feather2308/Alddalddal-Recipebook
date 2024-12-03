package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.entity.FavoriteId;
import com.team9.alddalddal.repository.AccountRepository;
import com.team9.alddalddal.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

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

    public Account getAccount(String id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    public List<Favorite> getFavoriteById(String id) {
        return favoriteRepository.findById_Id(id);
    }

    public List<Favorite> getFavoriteByName(String name) {
        return favoriteRepository.findById_Name(name);
    }

    public boolean isFavorite(String id, String name) {
        Optional<Favorite> favorite = favoriteRepository.findById_IdAndId_Name(id, name);
        return favorite.isPresent();
    }

    public Optional<Favorite> getFavorite(String id, String name) {
        return favoriteRepository.findById_IdAndId_Name(id, name);
    }

    public void addFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public void addFavorite(String id, String name) {
        Favorite favorite = new Favorite(id, name);
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public void deleteFavorite(String id, String name) {
        Favorite favorite = new Favorite(id, name);
        favoriteRepository.delete(favorite);
    }
}