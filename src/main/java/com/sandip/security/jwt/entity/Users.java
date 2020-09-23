package com.sandip.security.jwt.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
@Table(name = "users_table")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter @Setter
    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @Column(name = "user_id")
    private long userId;

    @Getter @Setter
    @Column(name = "username", unique = true)
    private String username;

    @Getter @Setter
    @Column(name = "role")
    private String role;

    @Getter @Setter
    @Column(name = "password")
    private String password;

    public Users(String username, String role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }
}
