package com.team9.alddalddal.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}