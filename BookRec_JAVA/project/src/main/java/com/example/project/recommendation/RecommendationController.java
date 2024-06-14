package com.example.project.recommendation;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserRepository;
import com.example.project.authentication.user.UserSession;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.readingList.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/recommendations")
@RestController
public class RecommendationController {


    @Autowired
    private ReadingListRepository readingListRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSession userSession;

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommended-books")
    public List<Book> getRecommendationsForCurrentUser() {
        // use user
        String username = UserSession.getInstance().getUsername();

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Long userId = currentUser.getId();

        return recommendationService.recommendBooks(userId);
    }
}

