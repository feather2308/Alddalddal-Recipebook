package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Ingredient {
    @Id
    @Column(name = "ingredient_name")
    private String name;

    @Column(name = "ingredient_lore")
    private String lore;
    @Column(name = "ingredient_image")
    private String image;

    @OneToMany(mappedBy = "ingredient")
    private List<Recipe_Ingredient> recipeIngredientList;
}
