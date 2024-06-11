package com.example.project.review;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserRepository;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import com.example.project.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping(value = "/add-review/{bookId}/{rating}")
    public ResponseEntity<Review> createReview(@PathVariable Long bookId, @PathVariable int rating) {


        User currentUser = userRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));

        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        // Find the book by ID
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = new Review();
        // Set the user and book for the review

        review.setRating(rating);
        review.setUser(currentUser);
        review.setBook(book);

        // Save the review
        Review savedReview = reviewRepository.save(review);

        // Return the saved review
        return ResponseEntity.ok(savedReview);
    }


    // UPDATE REVIEW
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PutMapping("update-review/{bookId}/{userId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody Review reviewUpdate) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (currentUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        // use book repository
        Book currentBook = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return reviewRepository.findById(reviewUpdate.getId())
                .map(review -> {
                    review.setRating(reviewUpdate.getRating());
                    review.setUser(currentUser);
                    review.setBook(currentBook);
                    Review updatedReview = reviewRepository.save(review);
                    return ResponseEntity.ok().body(updatedReview);
                }).orElse(ResponseEntity.notFound().build());
    }



    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PutMapping("update-review/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review reviewUpdate) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(reviewUpdate.getRating());
                    review.setUser(reviewUpdate.getUser());
                    review.setBook(reviewUpdate.getBook());
                    Review updatedReview = reviewRepository.save(review);
                    return ResponseEntity.ok().body(updatedReview);
                }).orElse(ResponseEntity.notFound().build());
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