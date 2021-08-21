package com.alos.helloworld.repository;

import java.util.ArrayList;
import java.util.List;

import com.alos.helloworld.entities.Library;

import org.springframework.beans.factory.annotation.Autowired;

public class LibraryRepositoryImpl  implements LibraryRepositoryCustom {

    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public List<Library> findAllByAuthor(String authorName) {
        List<Library> bookWithAuthor = new ArrayList<>();
        List<Library> booksList = libraryRepository.findAll();
        for(Library book : booksList) {
            if(book.getAuthor().equals(authorName)) {
                bookWithAuthor.add(book);
            }
        }
        return bookWithAuthor;
    }

}
