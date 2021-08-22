package com.alos.library_list_application.utils;

import com.alos.library_list_application.entities.Library;

public class LibraryUtils {

    public static Library mockBuildLibrary() {
        Library libraryMock = new Library();

        libraryMock.setAisle(123);
        libraryMock.setBookName("TestBook");
        libraryMock.setAisle(123);
        libraryMock.setAuthor("TestAuthor");
        libraryMock.setIsbn("test");
        libraryMock.setId(libraryMock.getIsbn() + libraryMock.getAisle());

        return libraryMock;
    }
}
