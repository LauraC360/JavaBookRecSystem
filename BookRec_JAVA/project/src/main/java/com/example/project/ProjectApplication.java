package com.example.project;

import com.example.project.authentication.user.UserRepository;
import com.example.project.author.AuthorRepository;
import com.example.project.book.BookRepository;
import com.example.project.readingList.ReadingListRepository;
import com.example.project.review.Review;
import com.example.project.review.ReviewRepository;
import com.example.project.userPreferences.UserPreferencesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, AuthorRepository.class, BookRepository.class, ReviewRepository.class, ReadingListRepository.class, UserPreferencesRepository.class})
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}


/// Swagger-UI : http://localhost:8080/swagger-ui/index.html#/

