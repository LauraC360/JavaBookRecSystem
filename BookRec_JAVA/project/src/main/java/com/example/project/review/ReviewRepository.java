package com.example.project.review;

import com.example.project.authentication.user.User;
import com.example.project.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndBook(User user, Book book);
    List<Review> findByUserId(Long userId);

    List<Long> findAllUserIds();
}