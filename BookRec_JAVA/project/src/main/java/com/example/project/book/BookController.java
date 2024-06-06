package com.example.project.book;

import com.example.project.author.Author;
import com.example.project.author.AuthorRepository;
import com.example.project.author.AuthorService;
import com.example.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/books")
public class BookController {
    @Autowired
    private final BookService bookService;

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final AuthorRepository authorRepository;


    @Autowired
    public BookController(BookService bookService,
                          BookRepository bookRepository,
                          AuthorRepository authorRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Get the list of books
    // TODO test this
    // Tested with Postman : http://localhost:8081/api/v1/books/getAllBooks
    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @GetMapping(value="/getAllBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    // Add a new book - POST request
    // Add a new book - POST request
//    @PostMapping("/addBook")
//    public ResponseEntity<Book> addBook(
//            @RequestParam String title,
//            @RequestParam String authorName,
//            @RequestParam String description,
//            @RequestParam String genres,
//            @RequestParam Double avgRating,
//            @RequestParam Long numRatings,
//            @RequestParam String url) {
//
//        // Retrieve existing author by name
//        Author author = authorRepository.findByNameNamed(authorName);
//        if (author == null) {
//            // If the author does not exist, create a new one
//            author = new Author();
//            author.setName(authorName);
//            author = authorRepository.save(author);
//        }
//
//        // Create new book
//        Book book = new Book(title, author, description, genres, avgRating, numRatings, url);
//        Book savedBook = bookRepository.save(book);
//
//        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//    }


    /// ADD A NEW BOOK
    /// TODO test this
    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @PostMapping(value="/addBook", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book addBook(@RequestBody Book newBook) {
        System.out.println(newBook.toString());
        return bookRepository.save(newBook);
    }


//    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
//    @PostMapping(value="/addRecipe", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Recipe addRecipe(@RequestBody Recipe newRecipe) {
//        System.out.println(newRecipe.toString());
//        return recipeRepository.save(newRecipe);
//    }

    // TODO test this
    // Modify title for an existing book - PUT request
    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @PutMapping(value="/updateBookName/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBookName(@PathVariable Long id, @RequestBody String newName) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setTitle(newName);
        Book updatedBook = bookRepository.save(book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


    // Delete a book - DELETE request
    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        // Remove associations
        book.setAuthor(null);
        bookRepository.save(book);  // Save the book without the associations

        bookRepository.delete(book);  // Now you can delete the book
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//
//    // Get the longest sequence of books in the recommended reading order
//    @GetMapping("/getLongestSequence")
//    public ResponseEntity<List<Book>> getLongestSequence() {
//        List<Book> longestSequence = bookService.findLongestSequence();
//        return new ResponseEntity<>(longestSequence, HttpStatus.OK);
//    }


//
//    @GetMapping(path = "/initialize")
//    public void initialize() {
//        DatabaseInitializer databaseInitializer = new DatabaseInitializer(bookRepository, authorRepository, publishingHouseRepository);
//        databaseInitializer.init();
//    }

}