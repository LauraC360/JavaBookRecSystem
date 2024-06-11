package com.example.project.book;

import com.example.project.author.Author;
import com.example.project.author.AuthorRepository;
import com.example.project.author.AuthorService;
import com.example.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// TODO CHANGE BOOKS CONTROLLER TO MATCH THE PURPOSE OF APPLICATION
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

    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @GetMapping(value="/getBook/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }


    // Tested with Postman : http://localhost:8082/api/v1/books/getBookPage/1
    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @GetMapping(value="/getBookPage/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBooksForPage(@PathVariable int page) {
        String booksJson = bookRepository.getBooksForPage(page);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return new ResponseEntity<>(booksJson, headers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @GetMapping(value="/getTotalBookPages", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getTotalBooks() {
        return bookRepository.getTotalBooks();
    }

    @CrossOrigin(origins = "http://localhost:3000") // Replace with your React app's URL
    @GetMapping(value="/getBooksByGenre/{page}/{genre}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBooksByGenre(@PathVariable int page, @PathVariable String genre) {
        String booksJson = bookRepository.getBooksByGenre(page, genre);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return new ResponseEntity<>(booksJson, headers, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value="/mostPopular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMostPopular(){
        String booksJson =  bookRepository.getMostPopularBooks();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return new ResponseEntity<>(booksJson, headers, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value="/someReadingList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSomeFromReadingLists(){
        //todo add user

        String booksJson =  bookRepository.getSomeBooksFromReadingList(2);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return new ResponseEntity<>(booksJson, headers, HttpStatus.OK);
    }


    /// COMMMENTTT

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