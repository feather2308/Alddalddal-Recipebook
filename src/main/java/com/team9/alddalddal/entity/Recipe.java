package com.team9.alddalddal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {
    @EmbeddedId
    private RecipeId id;

    @Column(name = "recipe_method")
    private String method;
    @Column(name = "recipe_garnish")
    private String garnish;
    @Column(name = "recipe_url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "cocktail_name", referencedColumnName = "cocktail_name", insertable = false, updatable = false)
    private Cocktail cocktail;

    public Recipe() {}
    public Recipe(Cocktail cocktail, String method, String garnish, String url) {
        this.cocktail = cocktail;
        this.id = new RecipeId(cocktail.getName());
        this.method = method;
        this.garnish = garnish;
        this.url = url;
    }
}
