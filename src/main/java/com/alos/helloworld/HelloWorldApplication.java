package com.alos.helloworld;

import com.alos.helloworld.entities.Library;
import com.alos.helloworld.repository.LibraryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldApplication implements CommandLineRunner {
	@Autowired
	LibraryRepository libraryRepository;
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

	@Override
	public void run(String[] args) {
		Library library = libraryRepository.findById("fdsefr343").get();
		System.out.println(library.getAuthor());
		// libraryRepository.findAll();
	}

}
