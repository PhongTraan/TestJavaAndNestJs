package com.example.CodeDemoUntiTest.dto.response;


import java.time.LocalDate;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private LocalDate dod;
    Set<String> roles;
}
