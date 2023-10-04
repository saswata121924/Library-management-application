package com.example.librarymanagement.controllers;

import com.example.librarymanagement.security.User;
import com.example.librarymanagement.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue")
    public String issueBook(@RequestParam("bookId") int bookId, @RequestParam("studentId") int studentId) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transactionService.issueBook(bookId, user.getStudent().getId());
    }

    @PostMapping("/transaction/return")
    public String returnBook(@RequestParam("bookId") int bookId) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transactionService.returnBook(bookId, user.getStudent().getId());
    }
}
