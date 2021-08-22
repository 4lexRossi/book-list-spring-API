package com.alos.library_list_application.controller.services;

import java.util.Optional;

import com.alos.library_list_application.entities.Library;
import com.alos.library_list_application.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbn, Integer aisle) {
        if(isbn.length() != 0 || aisle != null) {
            return isbn + aisle;
        }
        return "Missing isbn or aisle value(s)";
    }

    public boolean checkBookAlreadyExist(String id) {
        boolean hasBook = false;
        Optional<Library> lib = libraryRepository.findById(id);
        if(lib.isPresent()) {
            hasBook = true;
            return hasBook;
        }
        return hasBook;
    }

    public Library getBookById(String id) {
        return libraryRepository.findById(id).get();
    }
}
