package com.example.CodeDemoUntiTest.controller;


import com.example.CodeDemoUntiTest.dto.request.ApiResponse;
import com.example.CodeDemoUntiTest.dto.request.AuthenticationRequest;
import com.example.CodeDemoUntiTest.dto.request.IntrospectRequest;
import com.example.CodeDemoUntiTest.dto.request.RegisterRequest;
import com.example.CodeDemoUntiTest.dto.response.AuthenticationResponse;
import com.example.CodeDemoUntiTest.dto.response.IntrospectResponse;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.entity.User;
import com.example.CodeDemoUntiTest.service.Impl.AuthServiceImpl;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    private ApiResponse<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(authService.registerRequest(request))
                .build();
    }

    @PostMapping("/login")
    private ApiResponse<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        var result = authService.login(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    private ApiResponse<IntrospectResponse> login(@Valid @RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
