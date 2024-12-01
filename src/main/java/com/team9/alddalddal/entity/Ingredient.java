package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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
}
