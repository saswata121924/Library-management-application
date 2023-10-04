package com.example.librarymanagement.requests;

import com.example.librarymanagement.models.Author;
import com.example.librarymanagement.models.Book;
import com.example.librarymanagement.models.Genre;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private Genre genre;

    @NotBlank
    private String authorName;

    @NotBlank
    @Email
    private String email;

    public Book to(){
        Author author = Author.builder()
                .name(authorName)
                .email(email)
                .build();

        return Book.builder()
                .name(name)
                .genre(genre)
                .author(author)
                .build();
    }
}
