package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Cocktail {
    @Id
    @Column(name = "cocktail_name")
    private String name;

    @Column(name = "cocktail_image")
    private String image;
    @Column(name = "cocktail_easylore")
    private String easylore;
    @Column(name = "cocktail_lore")
    private String lore;

    @OneToMany(mappedBy = "cocktail")
    private List<Favorite> favoriteList;

    @OneToMany(mappedBy = "cocktail")
    private List<Comments> commentList;

    @OneToMany(mappedBy = "cocktail")
    private List<Cocktail_Tag> cocktailTagList;

    @OneToMany(mappedBy = "cocktail")
    private List<Recipe_Ingredient> recipeIngredientList;

    @OneToMany(mappedBy = "cocktail")
    private List<Recipe> recipeList;
}
