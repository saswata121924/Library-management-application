package com.example.librarymanagement.services;

import com.example.librarymanagement.models.Author;
import com.example.librarymanagement.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public Author createOrGetAuthor(Author author){
        Author result = authorRepository.findAuthor(author.getEmail());
        if (result==null){
            result = authorRepository.save(author);
        }
        return result;
    }
}
