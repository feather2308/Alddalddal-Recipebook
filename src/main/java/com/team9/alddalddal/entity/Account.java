package com.team9.alddalddal.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @Column(name = "account_id")
    private String id;

    @Column(name = "account_pw")
    private String pw;
    @Column(name = "account_name")
    private String name;
    @Column(name = "account_nickname")
    private String nickname;
    @Column(name = "account_email")
    private String email;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favoriteList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> commentList;
}