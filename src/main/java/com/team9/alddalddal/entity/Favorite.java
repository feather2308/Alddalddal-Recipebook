package com.team9.alddalddal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Favorite {
    @EmbeddedId
    private FavoriteId id;

    @MapsId("account_id")
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Account account;

    @MapsId("cocktail_name")
    @ManyToOne
    @JoinColumn(name = "cocktail_name", referencedColumnName = "cocktail_name", insertable = false, updatable = false)
    private Cocktail cocktail;

    public Favorite() {}
    public Favorite(Account account, Cocktail cocktail) {
        this.account = account;
        this.cocktail = cocktail;
        this.id = new FavoriteId(account.getId(), cocktail.getName());
    }
}
