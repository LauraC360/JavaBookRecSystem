package com.example.project.book;

import com.example.project.author.Author;
import com.example.project.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void generateAuthorData() {
        // Fetch all books
        Iterable<Book> books = bookRepository.findAll();

        // Create a set to store unique authors
        Set<Author> authors = new HashSet<>();

        // Iterate over all books
        for (Book book : books) {
            // Retrieve the author of each book
            Author author = book.getAuthor();

            // Add the author to the set
            authors.add(author);
        }

        // Save all authors in the author table
        authorRepository.saveAll(authors);
    }


    /// BONUS METHODS

    // Method to determine the recommended reading order between two books
//    private boolean isRecommendedAfter(Book bookA, Book bookB) {
//        // Compare their publication years for ordering
//        return bookA.getYear() < bookB.getYear();
//    }

//    // Method to find the longest sequence of books following the recommended order
//    public List<Book> findLongestSequence() {
//        List<Book> allBooks = bookRepository.findAllByOrderByYearAsc();
//        Map<Long, List<Book>> memo = new HashMap<>();
//
//        List<Book> longestSequence = new ArrayList<>();
//        for (Book book : allBooks) {
//            List<Book> sequence = findLongestSequenceFromBook(book, memo);
//            if (sequence.size() > longestSequence.size()) {
//                longestSequence = sequence;
//            }
//        }
//
//        return longestSequence;
//    }

//    private List<Book> findLongestSequenceFromBook(Book book, Map<Long, List<Book>> memo) {
//        if (memo.containsKey(book.getId())) {
//            return memo.get(book.getId());
//        }
//
//        List<Book> longestSequence = new ArrayList<>();
//        for (Book nextBook : book.getRecommendedAfter()) {
//            List<Book> sequence = findLongestSequenceFromBook(nextBook, memo);
//            if (sequence.size() > longestSequence.size()) {
//                longestSequence = new ArrayList<>(sequence);
//            }
//        }
//
//        longestSequence.add(0, book);
//        memo.put(book.getId(), longestSequence);
//
//        return longestSequence;
//    }
//
//    // Method to update recommended reading order for a new book
//    public void updateRecommendedReadingOrder(Book newBook) {
//        // Get all existing books from the database
//        List<Book> existingBooks = bookRepository.findAll();
//
//        // Iterate through existing books to determine recommended reading order for the new book
//        for (Book existingBook : existingBooks) {
//            if (isRecommendedAfter(existingBook, newBook)) {
//                // If existingBook is recommended to be read after newBook,
//                // add newBook to the recommendedAfter set of existingBook
//                existingBook.getRecommendedAfter().add(newBook);
//                bookRepository.save(existingBook); // Save the changes
//            } else if (isRecommendedAfter(newBook, existingBook)) {
//                // If newBook is recommended to be read after existingBook,
//                // add existingBook to the recommendedAfter set of newBook
//                newBook.getRecommendedAfter().add(existingBook);
//            }
//            // Save the changes to newBook outside the loop
//        }
//        // Save the newBook after all recommendations are processed
//        bookRepository.save(newBook);
//    }

//    // use the full constructor to create a new book (15 parameters)
//    public Book createBook(String title, Author author, String genre, Double price, Integer quantity, PublishingHouse publishingHouse, Integer year, Integer pages, String isbn, String cover, String format, String language, Double rating, String description, String keywords) {
//        Book book = new Book(title, author, genre, price, quantity, publishingHouse, year, pages, isbn, cover, format, language, rating, description, keywords);
//        bookRepository.save(book);
//        return book;
//    }
//
//    public Optional<Book> findBookById(Long id) {
//        return bookRepository.findById(id);
//    }
//
//    public void deleteBook(Long id) {
//        bookRepository.deleteById(id);
//    }
//
//    public Book updateBook(Long id, String title, Author author, String genre, Double price, Integer quantity, PublishingHouse publishingHouse, Integer year, Integer pages, String isbn, String cover, String format, String language, Double rating, String description, String keywords) {
//        Book book = new Book(title, author, genre, price, quantity, publishingHouse, year, pages, isbn, cover, format, language, rating, description, keywords);
//        book.setId(id);
//        bookRepository.save(book);
//        return book;
//    }
}