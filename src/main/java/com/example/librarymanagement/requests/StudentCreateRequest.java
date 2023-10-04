package com.example.librarymanagement.requests;

import com.example.librarymanagement.models.Student;
import com.example.librarymanagement.models.UserType;
import com.example.librarymanagement.security.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCreateRequest {
    @NotBlank
    private String name;

    @Positive
    private int age;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private UserType userType;

    private String phoneNumber;

    @NotBlank
    @Size(min = 8, max = 14)
    private String password;

    public Student to(){
        return Student.builder()
                .name(name)
                .age(age)
                .email(email)
                .phoneNumber(phoneNumber)
                .userType(userType)
                .user(
                        User.builder()
                                .username(this.email)
                                .password(this.password)
                                .build()
                )
                .build();
    }

}
