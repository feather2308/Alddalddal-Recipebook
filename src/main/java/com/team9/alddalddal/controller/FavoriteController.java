package com.team9.alddalddal.controller;

import com.team9.alddalddal.FavoriteRequest;
import com.team9.alddalddal.entity.Favorite;
import com.team9.alddalddal.entity.FavoriteId;
import com.team9.alddalddal.service.AccountService;
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

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        String accountId = favoriteRequest.getId();
        String cocktailName = favoriteRequest.getCocktailName();

        // 해당 계정이 이미 좋아요한 칵테일이 있는지 확인
        Optional<Favorite> existingFavorite = accountService.getFavorite(accountId, cocktailName);

        if (existingFavorite.isPresent()) {
            // 이미 좋아요한 경우, 삭제
            accountService.deleteFavorite(existingFavorite.get());
        } else {
            // 좋아요하지 않은 경우, 새로 추가
            Favorite favorite = new Favorite();
            favorite.setId(new FavoriteId(accountId, cocktailName));
            accountService.addFavorite(favorite);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");

        // 성공적으로 처리된 경우, HTTP 200 응답 반환
        return ResponseEntity.ok(response);
    }
}
