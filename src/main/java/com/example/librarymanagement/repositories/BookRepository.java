package com.example.librarymanagement.repositories;

import com.example.librarymanagement.models.Book;
import com.example.librarymanagement.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> getBookByAuthorName(String filterValue);

    List<Book> getBookByName(String filterValue);

    List<Book> getBookByGenre(Genre filterValue);

    @Query("select b from Book b where b.student.name = ?1")
    Book findByStudentName(String name);
}
