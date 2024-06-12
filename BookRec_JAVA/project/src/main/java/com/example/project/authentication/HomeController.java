package com.example.project.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/Home")
    public ResponseEntity<String> homePage() {
        return ResponseEntity.ok("Welcome to BookScroll App!");
    }
}