package com.example.project;

import com.example.project.author.Author;
import com.example.project.author.AuthorRepository;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CSVDataLoader {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public boolean isDataImported() {
        return bookRepository.count() > 0;
    }

    public void importCSV(String filePath) {
        if (isDataImported()) {
            System.out.println("Data already imported, skipping CSV import.");
            return;
        }

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    String id = csvRecord.get("Id"); // Read the Id but not use it
                    String title = csvRecord.get("Book");
                    String authorName = csvRecord.get("Author");

                    String description = csvRecord.get("Description");
                    String genres = csvRecord.get("Genres");
                    Double avgRating = Double.parseDouble(csvRecord.get("Avg_Rating"));
                    Long numRatings = Long.parseLong(csvRecord.get("Num_Ratings").replace(",", ""));
                    String url = csvRecord.get("URL");

                    // Fetch the Author entity from the repository
                    Author author = authorRepository.findByNameNamed(authorName);
                    if (author == null) {
                        String about = "About " + authorName;
                        author = new Author(authorName, about);
                        author = authorRepository.save(author);
                    }

                    Book book = new Book(title, author, description, parseGenresToList(genres), avgRating, numRatings, url);
                    bookRepository.save(book);
                } catch (Exception e) {
                    System.out.println("Parsing error! Skipping book at row: " + csvRecord);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> parseGenresToList(String genresString) {
        // Remove the square brackets and split by ', ' to get individual genres
        return Stream.of(genresString.substring(1, genresString.length() - 1).split(", "))
                .map(genre -> genre.trim().replaceAll("'", "")) // Remove the single quotes and trim whitespace
                .collect(Collectors.toList()); // Collect as a list
    }

}
