package com.team9.alddalddal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private int id;

    @Column(name = "comments_time")
    private Date time;
    @Column(name = "comments_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "cocktail_name")
    private Cocktail cocktail;

    public Comments() {}

    public Comments(Date time, String content, Account account, Cocktail cocktail) {
        this.time = time;
        this.content = content;
        this.account = account;
        this.cocktail = cocktail;
    }
}
