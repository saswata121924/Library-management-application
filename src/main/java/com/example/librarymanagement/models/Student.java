package com.example.librarymanagement.models;

import com.example.librarymanagement.security.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties(value = {"student", "transactionList"})
    private List<Book> bookList;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties(value = {"student", "book"})
    private List<Transaction> transactionList;

    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"student"})
    private User user;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}
