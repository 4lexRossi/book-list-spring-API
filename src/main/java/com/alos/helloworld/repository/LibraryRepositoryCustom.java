package com.alos.helloworld.repository;

import java.util.List;

import com.alos.helloworld.entities.Library;

public interface LibraryRepositoryCustom {
    List<Library> findAllByAuthor(String authorName);
}
