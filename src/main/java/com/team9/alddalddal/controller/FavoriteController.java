package com.team9.alddalddal.controller;

import com.team9.alddalddal.FavoriteRequest;
import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<Void> addFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        accountService.addFavorite(favoriteRequest.getId(), favoriteRequest.getCocktailName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        accountService.deleteFavorite(favoriteRequest.getId(), favoriteRequest.getCocktailName());
        return ResponseEntity.ok().build();
    }
}
