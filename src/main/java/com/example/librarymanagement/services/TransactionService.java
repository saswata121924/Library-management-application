package com.example.librarymanagement.services;

import com.example.librarymanagement.models.*;
import com.example.librarymanagement.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Value("${student.book.quota}")
    private int studentBookQuota;

    @Value("${book.return.days}")
    private int bookReturnMaxDays;

    @Value("${book.fine.day}")
    private int perDayFineAmount;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    public String issueBook(int bookId, int studentId) throws Exception {
        Student student = studentService.getStudentByID(studentId);
        if(student==null)
            throw new Exception("StudentId: "+studentId+ ", is not present");

        if(student.getBookList().size() >= studentBookQuota)
            throw new Exception("Student has reached their quota and will be unable to issue any new book");

        Book book = bookService.getBookById(bookId).get(0);
        if (book.getStudent()!=null)
            throw new Exception("Book is already issued and cannot be be issued right now");

        Transaction transaction = Transaction.builder()
                                            .book(book)
                                            .student(student)
                                            .transactionType(TransactionType.ISSUE)
                                            .transactionStatus(TransactionStatus.PENDING)
                                            .transactionId(UUID.randomUUID().toString())
                                            .build();
        transaction = transactionRepository.save(transaction);

        try{
            book.setStudent(student);
            bookService.insert(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);
        }catch (Exception e){
            book.setStudent(null);
            bookService.insert(book);

            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }
        return "Your book issue Txn Id is: "+transaction.getTransactionId();
    }

    public String returnBook(int bookId, int studentId) throws Exception {
        Student student = studentService.getStudentByID(studentId);
        Book book = bookService.getBookById(bookId).get(0);
        if(student==null || book==null ||
                book.getStudent()==null ||
                book.getStudent().getId()!=studentId)
            throw new Exception("Either student or book does not exist, or the book is not " +
                    "assigned to the student");

        List<Transaction> transactionList = transactionRepository.findByBookAndStudentAndTransactionTypeOrderByIdDesc(book, student, TransactionType.ISSUE);
        Transaction transactionIssue = transactionList.get(0);
        long issuedTime = transactionIssue.getUpdatedOn().getTime();
        long returnTime = System.currentTimeMillis();
        long days = TimeUnit.DAYS.convert(returnTime-issuedTime, TimeUnit.MILLISECONDS);
        int totalFine = 0;
        if(days > bookReturnMaxDays){
            totalFine = (int)(days- bookReturnMaxDays)*perDayFineAmount;
        }
        Transaction transaction = Transaction.builder()
                .book(book)
                .student(student)
                .transactionType(TransactionType.RETURN)
                .transactionStatus(TransactionStatus.PENDING)
                .transactionId(UUID.randomUUID().toString())
                .fine(totalFine)
                .build();
        transactionRepository.save(transaction);

        try{
            book.setStudent(null);
            bookService.insert(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);
        }catch (Exception e){
            book.setStudent(student);
            bookService.insert(book);

            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        }
        String s = "";
        if(totalFine>0)
            s = "You are late to return the book. You have to pay fine of Rs." + totalFine;
        else
            s = "You have returned the book within the timeframe.";

        return "Your book return Txn ID is " + transaction.getTransactionId() + ". " + s;
    }
}
