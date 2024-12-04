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
public class Tag {
    @Id
    @Column(name = "tag_id")
    private int id;

    @Column(name = "tag_trait")
    private String trait;

    @OneToMany(mappedBy = "tag")
    private List<Cocktail_Tag> cocktailTagList;
}
