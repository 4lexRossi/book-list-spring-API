package com.alos.library_list_application.repository;

import java.util.List;

import com.alos.library_list_application.entities.Library;

public interface LibraryRepositoryCustom {
    List<Library> findAllByAuthor(String authorName);
}
