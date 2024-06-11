package com.example.project.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(name = "Author.findById")
    Author findByIdNamed(@Param("myId") Long id);

    @Query(name = "Author.findByName")
    Author findByNameNamed(@Param("name") String name);
}