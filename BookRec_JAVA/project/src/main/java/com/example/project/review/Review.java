package com.example.project.review;

import com.example.project.book.Book;
import com.example.project.authentication.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating", nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Review() {
    }

    // constructor
    public Review(int rating, User user, Book book) {
        this.rating = rating;
        this.user = user;
        this.book = book;
    }
}