package com.team9.alddalddal.service;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.repository.AccountRepository;
import com.team9.alddalddal.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    public List<Favorite> getFavoritesByAccount(Account account) {
        return favoriteRepository.findByAccount(account);
    }

    public List<Favorite> getFavoritesByCocktail(Cocktail cocktail) {
        return favoriteRepository.findByCocktail(cocktail);
    }

    public boolean isFavorite(Account account, Cocktail cocktail) {
        Optional<Favorite> favorite = favoriteRepository.findByAccountAndCocktail(account, cocktail);
        return favorite.isPresent();
    }

    public Optional<Favorite> getFavoriteByAccountAndCocktail(Account account, Cocktail cocktail) {
        return favoriteRepository.findByAccountAndCocktail(account, cocktail);
    }

    public void addFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public void addFavorite(Account account, Cocktail cocktail) {
        Favorite favorite = new Favorite(account, cocktail);
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }

    public void deleteFavorite(Account account, Cocktail cocktail) {
        Favorite favorite = new Favorite(account, cocktail);
        favoriteRepository.delete(favorite);
    }

    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }
}