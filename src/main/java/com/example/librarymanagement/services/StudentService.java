package com.example.librarymanagement.services;

import com.example.librarymanagement.models.Student;
import com.example.librarymanagement.repositories.StudentRepository;
import com.example.librarymanagement.security.User;
import com.example.librarymanagement.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Value("${user.authority.student}")
    private String studentAuthority;

    @Value("${user.authority.admin}")
    private String adminAuthority;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentRepository studentRepository;
    public void insert(Student student){
        User user = student.getUser();
        if(student.getUserType().toString().equals(studentAuthority))
            user.setAuthority(studentAuthority);
        else
            user.setAuthority(adminAuthority);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        studentRepository.save(student);
    }

    public Student getStudentByID(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
}
