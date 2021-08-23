package com.alos.library_list_application.controller;

import java.util.List;

import com.alos.library_list_application.controller.services.LibraryService;
import com.alos.library_list_application.entities.Library;
import com.alos.library_list_application.entities.responses.AddResponse;
import com.alos.library_list_application.repository.LibraryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryService libraryService;

    @Autowired
    AddResponse addResponse;

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @GetMapping("/get-books")
    public List<Library> getAllBooks() {
        List<Library> allBooks = libraryRepository.findAll();
        return allBooks;
    }

    @GetMapping("/get-books/{id}")
    public Library getBookId(@PathVariable(value = "id")String id) {
        try {
            return libraryRepository.findById(id).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/get-books/author")
    public List<Library> getBookByAuthor(@RequestParam(value = "authorName")String authorName) {
        return libraryRepository.findAllByAuthor(authorName);
    }

    @PostMapping("/add-book")
    public ResponseEntity<AddResponse> addBookImplementation(@RequestBody Library library) {

        String id = libraryService.buildId(library.getIsbn(), library.getAisle());
        if(!libraryService.checkBookAlreadyExist(id)) {
            logger.info("Book is created successfully");
            library.setId(id);
            libraryRepository.save(library);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("uniqueId", id);

            addResponse.setMsg("Success book is added");
            addResponse.setId(id);
            return new ResponseEntity<>(addResponse, httpHeaders, HttpStatus.CREATED);
        }
        logger.info("Book already exist, can't create duplicated one");
        addResponse.setMsg("Book with id: " + id + " already exists");
        return new ResponseEntity<>(addResponse, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-book/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable(value = "id")String id, @RequestBody Library library) {

        Library existingBook = libraryService.getBookById(id);

        existingBook.setAisle(library.getAisle());
        existingBook.setAuthor(library.getAuthor());
        existingBook.setBookName(library.getBookName());
        libraryRepository.save(existingBook);

        return new ResponseEntity<>(existingBook, HttpStatus.OK);
    }

    @DeleteMapping("delete-book")
    public ResponseEntity<String> deleteBookById(@RequestBody Library library) {
        try {
            Library bookDeleted = libraryService.getBookById(library.getId());
            libraryRepository.delete(bookDeleted);
            return new ResponseEntity<>("Book is deleted", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
