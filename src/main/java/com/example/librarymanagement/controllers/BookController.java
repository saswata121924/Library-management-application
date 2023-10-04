package com.example.librarymanagement.controllers;

import com.example.librarymanagement.models.Book;
import com.example.librarymanagement.models.Genre;
import com.example.librarymanagement.requests.BookCreateRequest;
import com.example.librarymanagement.requests.BookFilterKey;
import com.example.librarymanagement.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public void createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        bookService.insert(bookCreateRequest.to());
    }

    @GetMapping("/book")
    public List<Book> getBook(@RequestParam("filterKey") String filterKey,
                              @RequestParam("filterValue") String filterValue) throws Exception {

        BookFilterKey bookFilterKey = BookFilterKey.valueOf(filterKey);

        switch (bookFilterKey){
            case AUTHOR_NAME:
                return bookService.getBookByAuthorName(filterValue);
            case BOOK_NAME:
                return bookService.getBookByName(filterValue);
            case BOOK_ID:
                return bookService.getBookById(Integer.parseInt(filterValue));
            case GENRE:
                return bookService.getBookByGenre(Genre.valueOf(filterValue));
            default:
                throw new Exception("Wrong filter type passed");
        }
    }

    @GetMapping("/book/all")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }
}
