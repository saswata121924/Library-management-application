package com.example.librarymanagement.services;

import com.example.librarymanagement.models.Author;
import com.example.librarymanagement.models.Book;
import com.example.librarymanagement.models.Genre;
import com.example.librarymanagement.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    public void insert(Book book){
        Author author = authorService.createOrGetAuthor(book.getAuthor());
        book.setAuthor(author);
        bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBookByAuthorName(String filterValue) {
        return bookRepository.getBookByAuthorName(filterValue);
    }

    public List<Book> getBookByName(String filterValue) {
        return bookRepository.getBookByName(filterValue);
    }

    public List<Book> getBookById(int id) {
        return bookRepository.findById(id).stream().toList();
    }

    public List<Book> getBookByGenre(Genre filterValue) {
        return bookRepository.getBookByGenre(filterValue);
    }
}
