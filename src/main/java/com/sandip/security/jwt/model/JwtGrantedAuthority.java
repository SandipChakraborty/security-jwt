package com.sandip.security.jwt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
public class JwtGrantedAuthority implements GrantedAuthority {

    @Getter @Setter
    private String authority;

    public JwtGrantedAuthority(String authority) {
        this.authority = authority;
    }
}
