package com.alos.helloworld.controller;

import com.alos.helloworld.entities.Library;
import com.alos.helloworld.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @Autowired
    LibraryRepository libraryRepository;

    @CrossOrigin
    @PostMapping("/add-book")
    public void addBookImplementation(@RequestBody Library library) {
        library.setId(library.getIsbn() + library.getAisle());
        libraryRepository.save(library);
    }
}
