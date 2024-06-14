package com.example.project.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.project.authentication.authority.Authority;
import com.example.project.authentication.authority.AuthorityRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);

        Authority authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority("ROLE_USER");
        authorityRepository.save(authority);

        return "User registered successfully";
    }

//    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
//    @PostMapping("/register-admin")
//    public String registerAdmin(@RequestBody User user) {
//        if (userRepository.findByUsername(user.getUsername()) != null) {
//            return "Username already exists";
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setEnabled(true);
//        userRepository.save(user);
//
//        Authority authority = new Authority();
//        authority.setUsername(user.getUsername());
//        authority.setAuthority("ROLE_ADMIN");
//        authorityRepository.save(authority);
//
//        return "Admin registered successfully";
//    }
}
