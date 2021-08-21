package com.alos.helloworld.controller.services;

import java.util.Optional;

import com.alos.helloworld.entities.Library;
import com.alos.helloworld.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbn, int aisle) {
        return isbn + aisle;
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
}
