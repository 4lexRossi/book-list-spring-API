package com.alos.helloworld;

import com.alos.helloworld.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldApplication {
	@Autowired
	LibraryRepository libraryRepository;
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

}
