package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class RecipeId implements Serializable {
    @Column(name = "cocktail_name")
    private String name;

    public RecipeId() {}
    public RecipeId(String cocktail_name) {
        this.name = cocktail_name;
    }
}
