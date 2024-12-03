package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cocktail_Tag {
    @EmbeddedId
    private Cocktail_TagId id;

    public Cocktail_Tag() {}
    public Cocktail_Tag(Cocktail_TagId id) {
        this.id = id;
    }
}
