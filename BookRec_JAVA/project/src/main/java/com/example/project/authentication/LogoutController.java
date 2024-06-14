package com.example.project.authentication;

import com.example.project.authentication.authority.Authority;
import com.example.project.authentication.authority.AuthorityRepository;
import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserRepository;
import com.example.project.authentication.user.UserSession;
import com.example.project.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LogoutController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PostMapping("/logout")
    public String logoutUser() {
        // Retrieve the username and password from the UserSession
        String username = UserSession.getInstance().getUsername();
        String password = UserSession.getInstance().getEncodedPassword();

        // Find the user by username
        User user = userRepository.findByUsername(username);

        // Check if the user exists and the password matches
        if (user != null && password.equals(user.getPassword())) {
            // Check if the user is an admin
            Authority authority = authorityRepository.findByUsername(username);
            if (authority != null && "ROLE_ADMIN".equals(authority.getAuthority())) {
                return "User cannot be logged out this way";
            }

            System.out.println("----------- Username:" + user.getUsername());
            System.out.println("Password: " + user.getPassword());
            System.out.println("Current Token:" + password);

            // Disable the user
            user.setEnabled(false);
            userRepository.save(user);

            // Clear the UserSession
            UserSession.getInstance().clear();

            return "User logged out successfully";
        }

        return "Invalid user data";
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}