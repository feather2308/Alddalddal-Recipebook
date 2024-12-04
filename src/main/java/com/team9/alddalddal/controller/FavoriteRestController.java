package com.team9.alddalddal.controller;

import com.team9.alddalddal.FavoriteRequest;
import com.team9.alddalddal.entity.Account;
import com.team9.alddalddal.entity.Cocktail;
import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.entity.FavoriteId;
import com.team9.alddalddal.service.AccountService;
import com.team9.alddalddal.service.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/favorite")
public class FavoriteRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CocktailService cocktailService;

    @PostMapping("/add")
    public ResponseEntity<Void> addFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        Cocktail cocktail = cocktailService.getCocktailByName(favoriteRequest.getCocktailName());
        Account account = accountService.getAccount(favoriteRequest.getId());
        accountService.addFavorite(account, cocktail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        Cocktail cocktail = cocktailService.getCocktailByName(favoriteRequest.getCocktailName());
        Account account = accountService.getAccount(favoriteRequest.getId());
        accountService.deleteFavorite(account, cocktail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        Cocktail cocktail = cocktailService.getCocktailByName(favoriteRequest.getCocktailName());
        Account account = accountService.getAccount(favoriteRequest.getId());

        if (accountService.isFavorite(account, cocktail)) {
            accountService.deleteFavorite(account, cocktail);
        } else {
            accountService.addFavorite(account, cocktail);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
