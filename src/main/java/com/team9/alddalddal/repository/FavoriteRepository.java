package com.team9.alddalddal.repository;

import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByAccount(Account account);
    List<Favorite> findByCocktail(Cocktail cocktail);

    Optional<Favorite> findByAccountAndCocktail(Account account, Cocktail cocktail);
}
