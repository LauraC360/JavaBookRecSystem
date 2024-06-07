package com.example.project.readingList;

import com.example.project.authentication.user.User;
import com.example.project.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "reading_lists")
public class ReadingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;


    @ElementCollection
    @CollectionTable(name = "reading_list_books", joinColumns = @JoinColumn(name = "reading_list_id"))
    private List<Long> books = new ArrayList<>(); // Changed from List<Book> to List<Long>

    public ReadingList(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public ReadingList() {
    }
}
