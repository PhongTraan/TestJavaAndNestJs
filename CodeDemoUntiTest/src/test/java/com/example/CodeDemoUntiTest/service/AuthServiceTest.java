package com.example.CodeDemoUntiTest.service;

import com.example.CodeDemoUntiTest.config.JWTService;
import com.example.CodeDemoUntiTest.dto.request.AuthenticationRequest;
import com.example.CodeDemoUntiTest.dto.request.IntrospectRequest;
import com.example.CodeDemoUntiTest.dto.request.RegisterRequest;
import com.example.CodeDemoUntiTest.dto.response.AuthenticationResponse;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.entity.User;
import com.example.CodeDemoUntiTest.exception.AppException;
import com.example.CodeDemoUntiTest.mapper.UserMapper;
import com.example.CodeDemoUntiTest.repository.AuthRepository;
import com.example.CodeDemoUntiTest.service.Impl.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthRepository authRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtService;

    private RegisterRequest request;
    private UserResponse userResponse;
    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;
    private IntrospectRequest introspectRequest;
    private User user;

    private LocalDate dob;


    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = RegisterRequest.builder()
                .name("phong1231")
                .email("phong3@gamil.com")
                .password("123455")
                .build();

        userResponse = UserResponse.builder()
                .id("1")
                .name("phong1231")
                .email("phong3@gamil.com")
                .dod(dob)
                .build();

        user = User.builder()
                .id(1)
                .name("phong1231")
                .email("phong3@gamil.com")
                .dod(dob)
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("phong3@gamil.com")
                .password("123455")
                .build();
    }

    @Test
    void register_validRequest_success() {
        when(authRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userMapper.registerUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(authRepository.save(user)).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse response = authService.registerRequest(request);

        assertEquals(userResponse, response);
        verify(authRepository).save(user);
    }

    @Test
    void register_validRequest_false() {
        when(authRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(AppException.class, () -> authService.registerRequest(request));
    }

    @Test
    void login_validRequest_success() {
        when(authRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())).thenReturn(true);

        String mockedToken = "mocked.jwt.token";
        when(jwtService.generateToken(user)).thenReturn(mockedToken);

        AuthenticationResponse response = authService.login(authenticationRequest);

        assertNotNull(response);
        assertTrue(response.isAuthenticated());
        assertNotNull(response.getToken());
        assertTrue(response.getToken().split("\\.").length == 3);

        verify(authRepository).findByEmail(authenticationRequest.getEmail());
        verify(passwordEncoder).matches(authenticationRequest.getPassword(), user.getPassword());
    }

    @Test
    void login_emailNotFound_throwsException() {
        when(authRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> authService.login(authenticationRequest));

        verify(authRepository).findByEmail(authenticationRequest.getEmail());
    }
}
