package com.example.project.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //List<Book> findAllByOrderByYearAsc();

    @Query(value = "SELECT * FROM get_recipes(?1)", nativeQuery = true)
    String getBooksForPage(int page);

    @Query(value = "SELECT * FROM get_total_pages()", nativeQuery = true)
    int getTotalBooks();

    @Query(value = "SELECT * FROM get_recipes_genre(?1, ?2)", nativeQuery = true)
    String getBooksByGenre(int page, String genre);

    @Query(value = "SELECT get_top_rated_books()", nativeQuery = true)
    String getMostPopularBooks();

    @Query(value = "SELECT * FROM get_top_rated_books()", nativeQuery = true)
    List<Book> findMostPopularBooks(Pageable pageable);

    @Query(value = "SELECT get_user_reading_list(?1)", nativeQuery = true)
    String getSomeBooksFromReadingList(int user_id);


    List<Book> findAllById(List<Long> ids);
}