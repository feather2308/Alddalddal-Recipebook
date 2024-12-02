package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe_Ingredient {
    @EmbeddedId
    private Recipe_IngredientId id;

    @Column(name = "recipe_ingredient_amount")
    private String amount;

    public Recipe_Ingredient() {}
    public Recipe_Ingredient(Recipe_IngredientId id, String amount) {
        this.id = id;
        this.amount = amount;
    }
}
