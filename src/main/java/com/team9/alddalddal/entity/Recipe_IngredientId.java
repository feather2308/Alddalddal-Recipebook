package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class Recipe_IngredientId implements Serializable {
    @Column(name = "cocktail_name")
    private String cname;
    @Column(name = "ingredient_name")
    private String iname;

    public Recipe_IngredientId() {}
    public Recipe_IngredientId(String cocktail_name, String ingredient_name) {
        this.cname = cocktail_name;
        this.iname = ingredient_name;
    }
}
