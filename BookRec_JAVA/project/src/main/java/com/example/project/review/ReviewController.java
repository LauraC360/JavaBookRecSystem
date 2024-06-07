package com.example.project.review;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserRepository;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PostMapping(value = "/add-review/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> createReview(@PathVariable Long bookId, @RequestBody Review review, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String currentUsername = null;
        if (session != null) {
            currentUsername = (String) session.getAttribute("user");
        }

        User currentUser = null;
        if (currentUsername != null) {
            currentUser = userRepository.findByUsername(currentUsername);
        }

        if (currentUser == null) {
            return ResponseEntity.status(401).body("Please log in first");
        }

        // Find the book by ID
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        // Set the user and book for the review
        review.setUser(currentUser);
        review.setBook(book);

        // Save the review
        Review savedReview = reviewRepository.save(review);

        // Return the saved review
        return ResponseEntity.ok(savedReview);
    }

//
//    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
//    @PutMapping("update-review/{id}")
//    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review reviewUpdate) {
//        return reviewRepository.findById(id)
//                .map(review -> {
//                    review.setRating(reviewUpdate.getRating());
//                    review.setUser(reviewUpdate.getUser());
//                    review.setBook(reviewUpdate.getBook());
//                    Review updatedReview = reviewRepository.save(review);
//                    return ResponseEntity.ok().body(updatedReview);
//                }).orElse(ResponseEntity.notFound().build());
//    }


    //    @GetMapping("/{id}")
//    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
//        return reviewRepository.findById(id)
//                .map(review -> ResponseEntity.ok().body(review))
//                .orElse(ResponseEntity.notFound().build());
//    }


//    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
//        return reviewRepository.findById(id)
//                .map(review -> {
//                    reviewRepository.delete(review);
//                    return ResponseEntity.ok().build();
//                }).orElse(ResponseEntity.notFound().build());
//    }
}