package com.team9.alddalddal.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Favorite {
    @EmbeddedId
    private FavoriteId id;

    public Favorite() {}
    public Favorite(String id, String name) {
        this.id = new FavoriteId(id, name);
    }
    public Favorite(FavoriteId id) {
        this.id = id;
    }
}
