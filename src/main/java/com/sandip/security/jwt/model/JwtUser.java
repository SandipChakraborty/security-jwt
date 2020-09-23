package com.sandip.security.jwt.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtUser {

    private String username;
    private long id;
    private List<JwtGrantedAuthority> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<JwtGrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<JwtGrantedAuthority> roles) {
        this.roles = roles;
    }

    public JwtUser() {
    }
}
