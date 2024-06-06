package com.example.project;

import com.example.project.author.AuthorRepository;
import com.example.project.book.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ProjectApplication.class, args);
		CSVDataLoader csvDataLoader = ctx.getBean(CSVDataLoader.class);
		AuthorRepository authorRepository = ctx.getBean(AuthorRepository.class);
		BookRepository bookRepository = ctx.getBean(BookRepository.class);

		if (authorRepository.count() == 0 && bookRepository.count() == 0) {
			try {
				csvDataLoader.importCSV("goodreads_data.csv");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


/// Swagger-UI : http://localhost:8080/swagger-ui/index.html#/

