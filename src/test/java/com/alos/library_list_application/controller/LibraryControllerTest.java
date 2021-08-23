package com.alos.library_list_application.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.*;

import static com.alos.library_list_application.utils.LibraryUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import com.alos.library_list_application.controller.services.LibraryService;
import com.alos.library_list_application.entities.Library;
import com.alos.library_list_application.entities.responses.AddResponse;
import com.alos.library_list_application.repository.LibraryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryControllerTest {

    @Autowired
    LibraryController libraryController;

    @MockBean
    LibraryRepository libraryRepository;

    @MockBean
    LibraryService libraryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_addBookTest_successfully() {

        Library lib = mockBuildLibrary();
        when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);

        ResponseEntity<?> responseEntity = libraryController.addBookImplementation(mockBuildLibrary());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        AddResponse addResponse = (AddResponse) responseEntity.getBody();
        String id = addResponse.getId();
        assertEquals(lib.getId(), id);
        assertEquals("Success book is added", addResponse.getMsg());

        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);

        responseEntity = libraryController.addBookImplementation(mockBuildLibrary());
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        addResponse = (AddResponse) responseEntity.getBody();
        assertEquals(lib.getId(), id);
        assertEquals("Book with id: " + id + " already exists", addResponse.getMsg());
    }

    @Test
    public void should_addBookTest_not_successfully() {

        Library lib = mockBuildLibrary();
        when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);
        when(libraryRepository.save(any())).thenReturn(lib);

        ResponseEntity<?> responseEntity = libraryController.addBookImplementation(mockBuildLibrary());
        assertNotEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);

        responseEntity = libraryController.addBookImplementation(mockBuildLibrary());
        assertNotEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    public void should_addBookTest_another_way_of_testing_successfully() throws Exception {

        Library lib = mockBuildLibrary();
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(lib);

        when(libraryService.buildId(lib.getIsbn(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(false);
        when(libraryRepository.save(any())).thenReturn(lib);
        this.mockMvc.perform(post("/add-book").contentType(MediaType.APPLICATION_JSON)
            .content(jsonResponse)).andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(lib.getId()))
            .andExpect(jsonPath("$.msg").value("Success book is added"));

        when(libraryService.checkBookAlreadyExist(lib.getId())).thenReturn(true);
        this.mockMvc.perform(post("/add-book").contentType(MediaType.APPLICATION_JSON)
            .content(jsonResponse)).andExpect(status().isAccepted())
            .andExpect(jsonPath("$.id").value(lib.getId()))
            .andExpect(jsonPath("$.msg").value("Book with id: " + lib.getId() + " already exists"));
    }

    @Test
    public void should_findAllByAuthorTest_successfully() throws Exception {

        List<Library> bookList = new ArrayList<>();
        bookList.add(mockBuildLibrary());
        bookList.add(mockBuildLibrary());

        when(libraryRepository.findAllByAuthor(any())).thenReturn(bookList);
        this.mockMvc.perform(get("/get-books/author").param("authorName", "TestAuthor"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", is(2)))
            .andExpect(jsonPath("$.[0].author").value(bookList.get(0).getAuthor()))
            .andExpect(jsonPath("$.[0].id").value(bookList.get(0).getId()));
    }

    @Test
    public void updateBookTest() throws Exception {
        Library lib = mockBuildLibrary();
        ObjectMapper mapper = new ObjectMapper();
        Library updateLibrary = mockUpdateLibrary(lib.getId());
        String jsonString = mapper.writeValueAsString(updateLibrary);

        when(libraryService.getBookById(any())).thenReturn(lib);
        this.mockMvc.perform(put("/update-book/" + lib.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(jsonString)).andExpect(status().isOk())
            .andExpect(content().json(jsonString));
    }

    @Test
    public void deleteBookTest() throws Exception {
        when(libraryService.getBookById(any())).thenReturn(mockBuildLibrary());
        doNothing().when(libraryRepository).delete(mockBuildLibrary());
        this.mockMvc.perform(delete("/delete-book").contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"test123\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("Book is deleted"));

    }
}
