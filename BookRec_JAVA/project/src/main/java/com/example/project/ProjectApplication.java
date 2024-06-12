package com.example.project;

import com.example.project.author.AuthorRepository;
import com.example.project.book.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
        // run
        ApplicationContext context = SpringApplication.run(ProjectApplication.class, args);
    }
}


/// Swagger-UI : http://localhost:8080/swagger-ui/index.html#/

