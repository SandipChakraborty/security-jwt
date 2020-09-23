package com.sandip.security.jwt.security;

import com.sandip.security.jwt.entity.Users;
import com.sandip.security.jwt.exception.CustomGeneralException;
import com.sandip.security.jwt.model.JwtGrantedAuthority;
import com.sandip.security.jwt.model.JwtUser;
import com.sandip.security.jwt.repo.UsersRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class JwtGenerator {

    private final long expirationInSec = 60; // 1 min
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UsersRepo usersRepo;

    @Value("${jwt.secret}")
    private String secret;

    public String generate(String username, String password) throws CustomGeneralException {

        Users users = usersRepo.findByUsername(username);
        if (users == null) {
            throw new RuntimeException("Wrong Username!");
        }
        if (bCryptPasswordEncoder.matches(password, users.getPassword())) {

            Claims claims = Jwts.claims().setExpiration(new Date(System.currentTimeMillis() + expirationInSec * 1000))
                    .setSubject(String.valueOf(users.getUserId()));
            claims.put("username", users.getUsername());
            List<JwtGrantedAuthority> jwtGrantedAuthorities = Arrays.asList(new JwtGrantedAuthority(users.getRole()));
            claims.put("role", jwtGrantedAuthorities);
            return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        } else {
            throw new CustomGeneralException("Wrong Password!");
        }
    }
}
