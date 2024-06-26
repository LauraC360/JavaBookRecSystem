package com.example.project.readingList;

import com.example.project.authentication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {
    ReadingList findByName(String name);

    // find user and name for a reading list
    ReadingList findByUserAndName(User user, String name);

    List<ReadingList> findByUser(User currentUser);
}