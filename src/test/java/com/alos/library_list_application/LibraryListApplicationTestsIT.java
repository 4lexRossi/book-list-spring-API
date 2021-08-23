package com.alos.library_list_application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class LibraryListApplicationTestsIT {

    @Test
    public void should_call_getAuthorNameBooksTest_with_success() {

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String expectedStr = "[{\"bookName\":\"Cypress\",\"id\":\"fda123\",\"isbn\":\"fda\",\"aisle\":123,\"author\":\"Alex\"},{\"bookName\":\"Devops\",\"id\":\"adf321\",\"isbn\":\"daf\",\"aisle\":321,\"author\":\"Alex\"}]";

        ResponseEntity responseEntity = testRestTemplate.getForEntity
        ("http://localhost:8080/get-books/author?authorName=Alex", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStr, responseEntity.getBody());
    }

    @Test
    public void should_call_getAuthorNameBooksTest_with_response_error() {

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String expectedStr = "[{\"bookName\":\"ERROR\",\"id\":\"fda123\",\"isbn\":\"fda\",\"aisle\":123,\"author\":\"Alex\"},{\"bookName\":\"Devops\",\"id\":\"adf321\",\"isbn\":\"daf\",\"aisle\":321,\"author\":\"Alex\"}]";

        ResponseEntity responseEntity = testRestTemplate.getForEntity
        ("http://localhost:8080/get-books/author?authorName=Alex", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(expectedStr, responseEntity.getBody());
    }
}
