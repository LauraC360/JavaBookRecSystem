package com.example.project.book;

import com.example.project.author.Author;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
//import jdk.xml.internal.JdkXmlFeatures;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :myId"),
        @NamedQuery(name = "Book.findLikeName", query = "SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :name,'%')")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author", referencedColumnName = "name", nullable = false)
    private Author author;

    @Column(name = "description", length = 10000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    private List<String> genres = new ArrayList<>();

    @Column(name = "avg_rating")
    private Double avgRating;

    @Column(name = "num_ratings")
    private Long numRatings;

    @Column(name = "url")
    private String url;

    //constructor
    public Book(String title, Author author, String description, List<String> genres, Double avgRating, Long numRatings, String url) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.genres = genres;
        this.avgRating = avgRating;
        this.numRatings = numRatings;
        this.url = url;
    }

    public Book() {
    }
}