package com.alos.library_list_application.controller.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.alos.library_list_application.entities.Library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.alos.library_list_application.utils.LibraryUtils.mockBuildLibrary;

@SpringBootTest
public class LibraryServiceTest {

    @Autowired
    LibraryService libraryService;

    @Test
    public void checkBuildIdLogic() {
        Library lib = mockBuildLibrary();
        String id = libraryService.buildId(lib.getIsbn(), lib.getAisle());
        assertEquals(lib.getIsbn() + lib.getAisle(), id);
    }

    @Test
    public void checkIfBookAlreadyExists() {
        Library lib = mockBuildLibrary();
        boolean idExists = libraryService.checkBookAlreadyExist(lib.getId());
        assertTrue(!idExists);
    }
}
