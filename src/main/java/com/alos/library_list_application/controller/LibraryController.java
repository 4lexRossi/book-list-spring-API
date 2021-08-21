package com.alos.library_list_application.controller;

import java.util.List;

import com.alos.library_list_application.controller.services.LibraryService;
import com.alos.library_list_application.entities.Library;
import com.alos.library_list_application.entities.responses.AddResponse;
import com.alos.library_list_application.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/add-book")
    public ResponseEntity<AddResponse> addBookImplementation(@RequestBody Library library) {

        String id = libraryService.buildId(library.getIsbn(), library.getAisle());
        if(!libraryService.checkBookAlreadyExist(id)) {
            library.setId(id);
            libraryRepository.save(library);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("uniqueId", id);

            addResponse.setMsg("Success book is added");
            addResponse.setId(id);
            return new ResponseEntity<>(addResponse, httpHeaders, HttpStatus.CREATED);
        }

        addResponse.setMsg("Book with id: " + id + " already exists");
        addResponse.setId(id);
        return new ResponseEntity<>(addResponse, HttpStatus.ACCEPTED);
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
}
