package com.sandip.security.jwt.controller;

import com.sandip.security.jwt.exception.CustomGeneralException;
import com.sandip.security.jwt.repo.UsersRepo;
import com.sandip.security.jwt.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    @GetMapping()
    public String getToken(@RequestParam String username, @RequestParam String password) throws CustomGeneralException {
        return jwtGenerator.generate(username, password);
    }

    @GetMapping("/hi")
    public ResponseEntity testException() throws CustomGeneralException {

        throw new CustomGeneralException("Custom Msg!!!!!", HttpStatus.CONFLICT);
//        return ResponseEntity.ok("");
    }
}
