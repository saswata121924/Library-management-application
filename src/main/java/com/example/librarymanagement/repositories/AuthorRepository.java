package com.example.librarymanagement.repositories;

import com.example.librarymanagement.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "select * from author where email = ?1", nativeQuery = true)
    Author findAuthor(String email);
}
