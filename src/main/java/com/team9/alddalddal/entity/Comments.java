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
    @Column(name = "cocktail_name")
    private String name;
    @Column(name = "account_id")
    private String uid;

    public Comments() {}

    public Comments(Date time, String content, String name, String uid) {
        this.time = time;
        this.content = content;
        this.name = name;
        this.uid = uid;
    }
}
