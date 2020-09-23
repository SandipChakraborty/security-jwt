package com.sandip.security.jwt.security;

import com.sandip.security.jwt.exception.CustomGeneralException;
import com.sandip.security.jwt.model.JwtAuthenticationToken;
import com.sandip.security.jwt.model.JwtGrantedAuthority;
import com.sandip.security.jwt.model.JwtUserDetails;
import com.sandip.security.jwt.model.JwtUser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @SneakyThrows
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();
        JwtUser jwtuser = validator.validate(token);
        if (jwtuser == null) {
            throw new CustomGeneralException("Jwt is invalid!!!");
        }
        return new JwtUserDetails(jwtuser.getUsername(), jwtuser.getId(), token, jwtuser.getRoles());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
