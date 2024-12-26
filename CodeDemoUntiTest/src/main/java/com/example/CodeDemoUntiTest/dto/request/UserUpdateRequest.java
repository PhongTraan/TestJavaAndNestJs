package com.example.CodeDemoUntiTest.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserUpdateRequest {
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    List<String> roles;
}
