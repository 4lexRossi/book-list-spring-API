package com.alos.library_list_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.alos.library_list_application.entities.Library;

import static com.alos.library_list_application.utils.LibraryUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class LibraryListApplicationTestsIT {

    @Test
    public void should_call_getAuthorNameBooksTest_with_success() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String expectedStr = "[{\"bookName\":\"Cypress\",\"id\":\"fda123\",\"isbn\":\"fda\",\"aisle\":123,\"author\":\"Alex\"},{\"bookName\":\"Devops\",\"id\":\"adf321\",\"isbn\":\"daf\",\"aisle\":321,\"author\":\"Alex\"}]";

        ResponseEntity<?> responseEntity = restTemplate.getForEntity
            ("http://localhost:8080/get-books/author?authorName=Alex", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStr, responseEntity.getBody());
    }

    @Test
    public void should_call_getAuthorNameBooksTest_with_response_error() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        String expectedStr = "[{\"bookName\":\"ERROR\",\"id\":\"fda123\",\"isbn\":\"fda\",\"aisle\":123,\"author\":\"Alex\"},{\"bookName\":\"Devops\",\"id\":\"adf321\",\"isbn\":\"daf\",\"aisle\":321,\"author\":\"Alex\"}]";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity
            ("http://localhost:8080/get-books/author?authorName=Alex", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(expectedStr, responseEntity.getBody());
    }

    @Test
    public void addBookIntegrationTest() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Library> request = new HttpEntity<Library>(mockBuildLibrary(), httpHeaders);
        ResponseEntity<String> response =  restTemplate.postForEntity("http://localhost:8080/add-book", request, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        System.out.println(response);
    }
}
