package com.team9.alddalddal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class Cocktail_TagId implements Serializable {
    @Column(name = "cocktail_name")
    private String name;
    @Column(name = "tag_id")
    private int tag;

    public Cocktail_TagId() {}
    public Cocktail_TagId(String name, int tag) {
        this.name = name;
        this.tag = tag;
    }
}
