package com.team9.alddalddal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {
    private String id;  // 사용자의 ID
    private String cocktailName;  // 칵테일의 이름

    // 기본 생성자
    public FavoriteRequest() {}

    // 생성자
    public FavoriteRequest(String id, String cocktailName) {
        this.id = id;
        this.cocktailName = cocktailName;
    }
}

