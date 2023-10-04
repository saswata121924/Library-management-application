package com.example.librarymanagement.repositories;

import com.example.librarymanagement.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
