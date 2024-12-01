package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {
    @Id
    @Column(name = "cocktail_name")
    private String name;

    @Column(name = "recipe_method")
    private String method;
    @Column(name = "recipe_url")
    private String url;
}
