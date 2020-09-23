package com.sandip.security.jwt.security;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sandip.security.jwt.exception.CustomGeneralException;
import com.sandip.security.jwt.model.JwtGrantedAuthority;
import com.sandip.security.jwt.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtValidator {

    @Value("${jwt.secret}")
    private String secret;

    public JwtUser validate(String token) throws CustomGeneralException {

        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            JwtUser jwtuser = new JwtUser();
            jwtuser.setId(Long.parseLong(body.getSubject()));
            jwtuser.setUsername(String.valueOf(body.get("username")));
            JsonArray jarray = new Gson().fromJson(body.get("role").toString(), JsonArray.class);
            JsonObject element = (JsonObject) jarray.get(0);
            jwtuser.setRoles(Arrays.asList(new JwtGrantedAuthority(element.get("authority").getAsString())));
            return jwtuser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomGeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
