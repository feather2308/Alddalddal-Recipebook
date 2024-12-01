package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cocktail {
    @Id
    @Column(name = "cocktail_name")
    private String name;

    @Column(name = "cocktail_image")
    private String image;
    @Column(name = "cocktail_lore")
    private String lore;
}
