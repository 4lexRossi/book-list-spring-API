package com.alos.helloworld.repository;

import com.alos.helloworld.entities.Library;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String>{

}
