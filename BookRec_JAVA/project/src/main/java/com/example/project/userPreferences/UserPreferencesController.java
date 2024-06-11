package com.example.project.userPreferences;

import com.example.project.authentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.project.authentication.user.UserRepository;

@RestController
@RequestMapping("/api/v1/preferences")
public class UserPreferencesController {

    @Autowired
    private final UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserPreferencesController(UserPreferencesRepository userPreferencesRepository, UserRepository userRepository) {
        this.userPreferencesRepository = userPreferencesRepository;
        this.userRepository = userRepository;
    }

    // Tested with Postman : http://localhost:8081/api/preferences/see-all-preferences/2
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value="see-all-preferences/{userId}")
    public ResponseEntity<UserPreferences> getUserPreferences(@PathVariable Long userId) {
        UserPreferences userPreferences = userPreferencesRepository.findByUserId(userId);
        return ResponseEntity.ok(userPreferences);
    }

    // Tested with Postman : http://localhost:8081/api/preferences/choose-preferences/2
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value="/choose-preferences/{userId}", consumes = "application/json")
    public ResponseEntity<UserPreferences> createUserPreferences(@PathVariable Long userId, @RequestBody UserPreferences userPreferences) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userPreferences.setUser(user);
        UserPreferences newUserPreferences = userPreferencesRepository.save(userPreferences);
        return ResponseEntity.ok(newUserPreferences);
    }

    // Tested with Postman : http://localhost:8081/api/preferences/update-preferences/2
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update-preferences/{userId}")
    public ResponseEntity<UserPreferences> updateUserPreferences(@PathVariable Long userId, @RequestBody UserPreferences userPreferences) {
        UserPreferences existingUserPreferences = userPreferencesRepository.findByUserId(userId);
        existingUserPreferences.setPreferredGenres(userPreferences.getPreferredGenres());
        UserPreferences updatedUserPreferences = userPreferencesRepository.save(existingUserPreferences);
        return ResponseEntity.ok(updatedUserPreferences);
    }
}