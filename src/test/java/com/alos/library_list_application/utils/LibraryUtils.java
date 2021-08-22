package com.alos.library_list_application.utils;

import com.alos.library_list_application.entities.Library;

public class LibraryUtils {

    public static Library mockBuildLibrary() {
        Library libraryMock = new Library();

        libraryMock.setAisle(123);
        libraryMock.setBookName("TestBook");
        libraryMock.setIsbn("test");
        libraryMock.setAuthor("TestAuthor");
        libraryMock.setId(libraryMock.getIsbn() + libraryMock.getAisle());

        return libraryMock;
    }

    public static Library mockUpdateLibrary(String id) {
        Library libraryUpdateMock = new Library();

        libraryUpdateMock.setAisle(456);
        libraryUpdateMock.setBookName("UpdateBook");
        libraryUpdateMock.setAuthor("UpdateAuthor");
        libraryUpdateMock.setIsbn(mockBuildLibrary().getIsbn());
        libraryUpdateMock.setId(id);

        return libraryUpdateMock;
    }
}
