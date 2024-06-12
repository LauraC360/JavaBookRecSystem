package com.example.project.authentication;

import com.example.project.authentication.user.UserRepository;
import com.example.project.authentication.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.project.authentication.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;

@RestController
@RequestMapping("/api/v1/public")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSession userSession;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        // Find the user by username
        User user = userRepository.findByUsername(loginRequest.getUsername());

        // Check if the user exists and the password matches
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            // Generate a unique token for the user
            String token = user.getPassword();

            System.out.println("----------- Username:" + loginRequest.getUsername());
            System.out.println("Token: " + token);


            // set to enabled
            user.setEnabled(true);
            userRepository.save(user);

            // Store the username and encoded password in the UserSession
            UserSession.getInstance().setUser(loginRequest.getUsername(), token);

            return "Login successful";
        }

        return "Invalid username or password";
    }
}
