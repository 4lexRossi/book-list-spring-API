package com.alos.library_list_application;

import com.alos.library_list_application.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryListApplication {
	@Autowired
	LibraryRepository libraryRepository;
	public static void main(String[] args) {
		SpringApplication.run(LibraryListApplication.class, args);
	}

}
