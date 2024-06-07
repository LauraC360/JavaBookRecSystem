package com.example.project.authentication;

import com.example.project.authentication.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.project.authentication.user.UserRepository;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request) {

        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", username);
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().body("Logout successful");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/home")
    public ResponseEntity<String> homePage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return ResponseEntity.ok("Welcome to BookScroll App!");
        } else {
            return ResponseEntity.status(401).body("Please log in first");
        }
    }
}
