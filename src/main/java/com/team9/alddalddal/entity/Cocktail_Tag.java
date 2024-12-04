package com.team9.alddalddal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cocktail_Tag {
    @EmbeddedId
    private Cocktail_TagId id;

    @MapsId("cocktail_name")
    @ManyToOne
    @JoinColumn(name = "cocktail_name", referencedColumnName = "cocktail_name", insertable = false, updatable = false)
    private Cocktail cocktail;

    @MapsId("tag_id")
    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", insertable = false, updatable = false)
    private Tag tag;

    public Cocktail_Tag() {}
    public Cocktail_Tag(Cocktail cocktail, Tag tag) {
        this.cocktail = cocktail;
        this.tag = tag;
        this.id = new Cocktail_TagId(cocktail.getName(), tag.getId());
    }
}
