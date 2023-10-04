package com.example.librarymanagement.repositories;

import com.example.librarymanagement.models.Book;
import com.example.librarymanagement.models.Student;
import com.example.librarymanagement.models.Transaction;
import com.example.librarymanagement.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByBookAndStudentAndTransactionTypeOrderByIdDesc(Book book, Student student,
                                                                          TransactionType transactionType);
}
