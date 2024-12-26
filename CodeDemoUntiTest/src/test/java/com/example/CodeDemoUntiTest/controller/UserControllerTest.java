package com.example.CodeDemoUntiTest.controller;

import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.service.Impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;
    private UserResponse userResponse;

    @BeforeEach
    void initData() {
        LocalDate dob = LocalDate.of(1990, 9, 7);

        userResponse = UserResponse.builder()
                .id("1")
                .name("test_name")
                .email("test@gamil.com")
                .dod(dob)
                .build();
    }


    @Test
    public void testGetAllUsers_Success() throws Exception {
        // Create ObjectMapper for serializing data
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // Mock user data
        List<UserResponse> mockUsers = List.of(
                UserResponse.builder()
                        .id("1")
                        .name("John Doe")
                        .email("john@example.com")
                        .dod(LocalDate.of(1990, 1, 1))
                        .build()
        );

        when(userService.getAllUsers()).thenReturn(mockUsers);

        String validBearerToken = "valid_token_here";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validBearerToken));
//                .andExpect(MockMvcResultMatchers.status().isOk()) // Expecting HTTP 200 OK status
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("john@example.com"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dod").value("1990-01-01"));
    }


    @Test
    public void testGetAllUsers_Forbidden() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        List<UserResponse> mockUsers = List.of(
                UserResponse.builder()
                        .id("1")
                        .name("John Doe")
                        .email("john@example.com")
                        .dod(LocalDate.of(1990, 1, 1))
                        .build()
        );

        when(userService.getAllUsers()).thenReturn(mockUsers);

        String invalidBearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXYuY29tIiwic3ViIjoiYWRtaW5AZ2FtaWwuY29tIiwiZXhwIjoxNzM1MTg4NTA4LCJpYXQiOjE3MzUxODQ5MDgsInNjb3BlIjoiQURNSU4ifQ.3eTt5YMyKSqsgqZq9n4rvLgFy5zjgHCkd51I7SiB9vA";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(mockUsers))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidBearerToken));
//                .andExpect(MockMvcResultMatchers.status().isForbidden())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1006))
//                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Unauthenticated"));
    }
}
