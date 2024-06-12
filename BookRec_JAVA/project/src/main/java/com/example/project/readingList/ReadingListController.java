package com.example.project.readingList;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserSession;
import com.example.project.book.Book;
import com.example.project.book.BookRepository;
import com.example.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.authentication.user.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reading-lists")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
public class ReadingListController {

    @Autowired
    private ReadingListRepository readingListRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSession userSession;


    // Tested with Postman (POST Request: http://localhost:8080/api/reading-lists/create)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PostMapping(value = "/new-list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> createReadingList(@RequestBody ReadingList readingList) {
        // Retrieve the username from the UserSession
        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu poate crea lista
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found with username " + username);
        }

        // if there is a list with the same currentUser and the same name, dont create it
        if (readingListRepository.findByUserAndName(currentUser, readingList.getName()) != null) {
            return ResponseEntity.status(409).build(); // Conflict
        }

        readingList.setUser(currentUser);
        ReadingList savedReadingList = readingListRepository.save(readingList);
        return ResponseEntity.ok(savedReadingList);
    }


    // Tested with Postman (PUT Request: http://localhost:8080/api/reading-lists/add-book/1/1)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PutMapping(value = "/{readingListId}/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> addBookToReadingList(@PathVariable Long readingListId,
                                                            @PathVariable Long bookId) {
        System.out.println("!!!readingListId: " + readingListId + " bookId: " + bookId);


        ReadingList readingList = readingListRepository.findById(readingListId)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found with id " + readingListId));

        System.out.println("!!! out2");

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));

        System.out.println("!!! out3");

        // use user data
        String username = UserSession.getInstance().getUsername();

        System.out.println("!!! out4");

        // daca nu e logat, nu merge
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        System.out.println("!!! out5");

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found with username " + username);
        }

        System.out.println("!!! out6");

        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        System.out.println("!!! out7");

        // if id is already in the list, dont put it anymore
        if (readingList.getBooks().contains(book.getId())) {
            return ResponseEntity.ok(readingList);
        }

        System.out.println("!!! out8");

        readingList.getBooks().add(book.getId());

        System.out.println("!!! out9");

        ReadingList updatedReadingList = readingListRepository.save(readingList);

        System.out.println("!!! out10");

        return ResponseEntity.ok(updatedReadingList);
    }

    // Tested with Postman (GET Request: http://localhost:8080/api/reading-lists/get/1)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @GetMapping(value = "list/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> getReadingList(@PathVariable Long id) {
        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found with id " + id));

        // use user
        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu merge
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found with username " + username);
        }

        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        return ResponseEntity.ok(readingList);
    }

    // Tested with Postman (DELETE Request: http://localhost:8080/api/reading-lists/remove/1)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @DeleteMapping(value = "/excluded/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeReadingList(@PathVariable Long id) {
        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found"));

        // use user
        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu merge
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found");
        }

        // verify if the currentUser is the owner of the readingList
        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        readingListRepository.delete(readingList);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @GetMapping(value = "/all-reading-lists", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReadingList> getAllReadingLists() {
        // use user

        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu merge
        if (username == null) {
            return null;
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return readingListRepository.findByUser(currentUser);
    }

    // delete a book from a list
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PutMapping(value = "/excluded/{readingListId}/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> deleteBookFromReadingList(@PathVariable Long readingListId, @PathVariable Long bookId) {
        ReadingList readingList = readingListRepository.findById(readingListId)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found with id " + readingListId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));

        // use user data
        String username = UserSession.getInstance().getUsername();

        // daca nu e logat, nu merge
        if (username == null) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Find the user by username
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new ResourceNotFoundException("User not found with username " + username);
        }

        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // if id is not in the list, dont delete it
        if (!readingList.getBooks().contains(book.getId())) {
            return ResponseEntity.ok(readingList);
        }

        readingList.getBooks().remove(book.getId());
        ReadingList updatedReadingList = readingListRepository.save(readingList);

        return ResponseEntity.ok(updatedReadingList);
    }

}
