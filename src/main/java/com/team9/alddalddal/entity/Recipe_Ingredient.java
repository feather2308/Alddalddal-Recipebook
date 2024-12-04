package com.team9.alddalddal.entity;

import jakarta.persistence.*;
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

    @MapsId("cocktail_name")
    @ManyToOne
    @JoinColumn(name = "cocktail_name", referencedColumnName = "cocktail_name", insertable = false, updatable = false)
    private Cocktail cocktail;

    @MapsId("ingredient_name")
    @ManyToOne
    @JoinColumn(name = "ingredient_name", referencedColumnName = "ingredient_name", insertable = false, updatable = false)
    private Ingredient ingredient;

    public Recipe_Ingredient() {}
    public Recipe_Ingredient(Cocktail cocktail, Ingredient ingredient, String amount) {
        this.cocktail = cocktail;
        this.ingredient = ingredient;
        this.id = new Recipe_IngredientId(cocktail.getName(), ingredient.getName());
        this.amount = amount;
    }
}
