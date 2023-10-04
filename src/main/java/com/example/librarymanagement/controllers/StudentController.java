package com.example.librarymanagement.controllers;

import com.example.librarymanagement.models.Student;
import com.example.librarymanagement.requests.StudentCreateRequest;
import com.example.librarymanagement.security.User;
import com.example.librarymanagement.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    // Permit All
    @PostMapping("/signup")
    public void createStudent(@Valid @RequestBody StudentCreateRequest studentCreateRequest){
        studentService.insert(studentCreateRequest.to());
    }

    // Admin
    @GetMapping("/student/{studentId}")
    public Student getStudentByStudentId(@PathVariable("studentId") int id){
        return studentService.getStudentByID(id);
    }

    // Admin
    @GetMapping("/student/all")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    // Student
    @GetMapping("/student/details")
    public Student getStudent(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return studentService.getStudentByID(user.getStudent().getId());
    }

}
