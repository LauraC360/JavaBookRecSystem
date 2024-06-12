package com.example.project.recommendation;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserRepository;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import com.example.project.review.Review;
import com.example.project.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private static final int NUM_RECOMMENDATIONS = 5;
    private static final int POPULAR_BOOKS_LIMIT = 10;

    public List<Book> recommendBooks(Long userId) {
        Map<Long, Integer> targetUserRatings = getUserRatings(userId);
        List<User> allUsers = userRepository.findAll();
        Map<Long, Double> similarityScores = new HashMap<>();

        for (User user : allUsers) {
            if (!user.getId().equals(userId)) {
                Map<Long, Integer> otherUserRatings = getUserRatings(user.getId());
                double similarity = calculateCosineSimilarity(targetUserRatings, otherUserRatings);
                similarityScores.put(user.getId(), similarity);
            }
        }

        List<Long> similarUsers = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Book> recommendations = new ArrayList<>();
        Set<Long> recommendedBookIds = new HashSet<>();

        for (Long similarUserId : similarUsers) {
            if (recommendations.size() >= NUM_RECOMMENDATIONS) break;
            Map<Long, Integer> similarUserRatings = getUserRatings(similarUserId);
            for (Map.Entry<Long, Integer> entry : similarUserRatings.entrySet()) {
                if (!targetUserRatings.containsKey(entry.getKey()) && !recommendedBookIds.contains(entry.getKey())) {
                    Book book = bookRepository.findById(entry.getKey()).orElse(null);
                    if (book != null) {
                        recommendations.add(book);
                        recommendedBookIds.add(entry.getKey());
                        if (recommendations.size() >= NUM_RECOMMENDATIONS) break;
                    }
                }
            }
        }

        if (recommendations.size() < NUM_RECOMMENDATIONS) {

            List<Book> popularBooks = bookRepository.findMostPopularBooks(PageRequest.of(0, POPULAR_BOOKS_LIMIT));
            for (Book book : popularBooks) {
                if (!recommendedBookIds.contains(book.getId())) {
                    recommendations.add(book);
                    recommendedBookIds.add(book.getId());
                    if (recommendations.size() >= NUM_RECOMMENDATIONS) break;
                }
            }
        }

        return recommendations;
    }

    private Map<Long, Integer> getUserRatings(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        Map<Long, Integer> ratings = new HashMap<>();
        for (Review review : reviews) {
            ratings.put(review.getBook().getId(), review.getRating());
        }
        return ratings;
    }

    private double calculateCosineSimilarity(Map<Long, Integer> ratingsA, Map<Long, Integer> ratingsB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (Long bookId : ratingsA.keySet()) {
            if (ratingsB.containsKey(bookId)) {
                dotProduct += ratingsA.get(bookId) * ratingsB.get(bookId);
                normA += Math.pow(ratingsA.get(bookId), 2);
                normB += Math.pow(ratingsB.get(bookId), 2);
            }
        }

        normA = Math.sqrt(normA);
        normB = Math.sqrt(normB);

        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        return dotProduct / (normA * normB);
    }
}
