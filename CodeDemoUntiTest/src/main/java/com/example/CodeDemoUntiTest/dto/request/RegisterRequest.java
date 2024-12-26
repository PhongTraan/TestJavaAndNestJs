package com.example.CodeDemoUntiTest.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @Size(min = 6, message = "USERNAME_INVALID")
    private  String name;
    private  String email;
    @Size(min = 6, message = "INVALID_PASSWORD")
    private  String password;
    private LocalDate dod;
}
