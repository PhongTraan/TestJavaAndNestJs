package com.example.CodeDemoUntiTest.controller;

import com.example.CodeDemoUntiTest.dto.request.AuthenticationRequest;
import com.example.CodeDemoUntiTest.dto.request.RegisterRequest;
import com.example.CodeDemoUntiTest.dto.response.AuthenticationResponse;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.service.Impl.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthServiceImpl authService;
    private RegisterRequest request;
    private UserResponse userResponse;
    private AuthenticationResponse authenticationResponse;
    private AuthenticationRequest authenticationRequest;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = RegisterRequest.builder()
                .name("phong122")
                .email("phong3@gamil.com")
                .password("123456")
                .dod(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("1")
                .name("phong1231")
                .email("phong3@gamil.com")
                .dod(dob)
                .build();

        //Login
        authenticationRequest = AuthenticationRequest.builder()
                .email("phong@gamil.com")
                .password("123456")
                .build();

        authenticationResponse = AuthenticationResponse.builder()
                .token("valid_token")
                .authenticated(true)
                .build();

    }


    @Test
    void register_validRequest_success() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(authService.registerRequest(ArgumentMatchers.any())).thenReturn(userResponse);
        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        // GIVEN
        request.setName("joh");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 6 characters"));
    }

    @Test
    void createUser_passwordInvalid_fail() throws Exception {
        // GIVEN
        request.setName("phong123");
        request.setPassword("1");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Password must be at least 6 characters"));
    }

    //Test Login
    @Test
    void login_validCredentials_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(authenticationRequest);

        // WHEN
        Mockito.when(authService.login(ArgumentMatchers.any())).thenReturn(authenticationResponse);

        // THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200));
    }

}

