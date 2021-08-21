package com.alos.library_list_application.repository;

import com.alos.library_list_application.entities.Library;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, String>, LibraryRepositoryCustom {



}
