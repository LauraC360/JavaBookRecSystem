package com.example.project.readingList;

import com.example.project.authentication.user.User;
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


    // Tested with Postman (POST Request: http://localhost:8080/api/reading-lists/create)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> createReadingList(@RequestBody ReadingList readingList) {
        // authorize the user
        // use user 2
        User currentUser = userRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));

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
    @PutMapping(value = "/add-book/{readingListId}/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> addBookToReadingList(@PathVariable Long readingListId, @PathVariable Long bookId) {
        ReadingList readingList = readingListRepository.findById(readingListId)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found with id " + readingListId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));

        // use user 2
        User currentUser = userRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));

        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // if id is already in the list, dont put it anymore
        if (readingList.getBooks().contains(book.getId())) {
            return ResponseEntity.ok(readingList);
        }

        readingList.getBooks().add(book.getId());
        ReadingList updatedReadingList = readingListRepository.save(readingList);

        return ResponseEntity.ok(updatedReadingList);
    }

    // Tested with Postman (GET Request: http://localhost:8080/api/reading-lists/get/1)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @GetMapping(value = "get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadingList> getReadingList(@PathVariable Long id) {
        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found with id " + id));

        // use user 2
        User currentUser = userRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));

        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        return ResponseEntity.ok(readingList);
    }

    // Tested with Postman (DELETE Request: http://localhost:8080/api/reading-lists/remove/1)
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @DeleteMapping(value = "remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeReadingList(@PathVariable Long id) {
        ReadingList readingList = readingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReadingList not found with id " + id));


        // use user 2
        User currentUser = userRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));

        if (!readingList.getUser().equals(currentUser)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        readingListRepository.delete(readingList);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
    @GetMapping(value = "/get-all-reading-lists", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReadingList> getAllReadingLists() {
        // use user 2
        User currentUser = userRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("User not found with id 2"));
        return readingListRepository.findByUser(currentUser);
    }
}
