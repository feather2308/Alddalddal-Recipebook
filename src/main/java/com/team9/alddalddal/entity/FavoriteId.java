package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class FavoriteId implements Serializable {
    @Column(name = "account_id")
    private String id;
    @Column(name = "cocktail_name")
    private String name;

    public FavoriteId() {}
    public FavoriteId(String account_id, String cocktail_name) {
        this.id = account_id;
        this.name = cocktail_name;
    }
}
