package com.example.project.review;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserRepository;
import com.example.project.authentication.user.UserSession;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import com.example.project.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
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

    // Tested with Postman (POST Request: http://localhost:8080/api/v1/reviews/add-review/1)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PostMapping(value = "/{bookId}/{rating}")
    public ResponseEntity<Review> createReview(@PathVariable Long bookId, @PathVariable int rating) {

        // use user
        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu merge
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        // Find the book by ID
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new RuntimeException("Book not found"));

        // if the book is not found, return 404
        if (book == null) {
            return ResponseEntity.status(404).body(null);
        }

        // If there already is a review from this user
        Review review = reviewRepository.findByUserAndBook(currentUser, book);
        if (review != null) {
            review.setRating(rating);
            Review savedReview = reviewRepository.save(review);
            // Return the saved review
            return ResponseEntity.ok(savedReview);
        }
        else {
            Review newReview = new Review();
            // Set the user and book for the review
            newReview.setRating(rating);
            newReview.setUser(currentUser);
            newReview.setBook(book);

            // Save the review
            Review savedReview = reviewRepository.save(newReview);
            // Return the saved review
            return ResponseEntity.ok(savedReview);
        }
    }


    // api for get review by book
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @GetMapping(value = "/{bookId}")
    public ResponseEntity<Review> getReviewByBook(@PathVariable Long bookId) {
        // Find the book by ID
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new RuntimeException("Book not found"));

        // if the book is not found, return 404
        if (book == null) {
            return ResponseEntity.status(404).body(null);
        }

        // use user
        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu merge
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);

        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        // If there already is a review from this user
        Review review = reviewRepository.findByUserAndBook(currentUser, book);
        if (review != null) {
            return ResponseEntity.ok(review);
        }
        else {
            return ResponseEntity.status(404).body(null);
        }
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


    //@GetMapping("/review/{id}")
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