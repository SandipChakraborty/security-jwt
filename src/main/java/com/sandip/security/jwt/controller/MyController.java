package com.sandip.security.jwt.controller;

import com.sandip.security.jwt.entity.Users;
import com.sandip.security.jwt.exception.CustomGeneralException;
import com.sandip.security.jwt.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsersRepo usersRepo;

    @PostMapping("/createUser")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity createUser(@RequestBody Users user) throws CustomGeneralException {
        if (usersRepo.findByUsername(user.getUsername()) != null) throw new CustomGeneralException("User already exists with username : " + user.getUsername(), HttpStatus.CONFLICT);
        user.setUserId(0);
        if (user.getRole().equalsIgnoreCase("Admin")) user.setRole("Admin");
        else user.setRole("User");
        String password = (user.getPassword() == null || user.getPassword().isEmpty()) ?
                bCryptPasswordEncoder.encode("password") : bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user = usersRepo.save(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword ) throws CustomGeneralException {
        Users user = usersRepo.findByUsername(username);
        if (user == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) throw new CustomGeneralException("Old password didn't match.", HttpStatus.FORBIDDEN);
        String password = (newPassword == null || newPassword.isEmpty()) ?
                bCryptPasswordEncoder.encode("password") : bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(password);
        user = usersRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/resetPassword")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity resetPassword(@RequestParam String username) throws CustomGeneralException {
        Users user = usersRepo.findByUsername(username);
        if (user == null) throw new CustomGeneralException(HttpStatus.NOT_FOUND);
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        usersRepo.save(user);
        return ResponseEntity.ok("Password Reset Successful.");
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAnyAuthority('Admin', 'User')")
    public ResponseEntity getUser(@RequestParam(required = false, name = "username") String username) throws CustomGeneralException {

        if (username == null || username.isEmpty()) {
            return ResponseEntity.ok(usersRepo.findAll());
        }
        Users user = usersRepo.findByUsername(username);
        if (user == null) throw new CustomGeneralException(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(user);
    }

}
