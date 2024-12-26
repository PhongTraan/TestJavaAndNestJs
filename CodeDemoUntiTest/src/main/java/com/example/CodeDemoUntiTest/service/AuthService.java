package com.example.CodeDemoUntiTest.service;

import com.example.CodeDemoUntiTest.dto.request.AuthenticationRequest;
import com.example.CodeDemoUntiTest.dto.request.IntrospectRequest;
import com.example.CodeDemoUntiTest.dto.request.RegisterRequest;
import com.example.CodeDemoUntiTest.dto.response.AuthenticationResponse;
import com.example.CodeDemoUntiTest.dto.response.IntrospectResponse;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.entity.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthService {
    UserResponse registerRequest (RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    String generateToken(User user);
    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

}
