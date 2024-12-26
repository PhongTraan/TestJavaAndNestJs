package com.example.CodeDemoUntiTest.controller;

import com.example.CodeDemoUntiTest.dto.request.ApiResponse;
import com.example.CodeDemoUntiTest.dto.request.UserUpdateRequest;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.service.Impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    private ApiResponse<List<UserResponse>> getAllUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userServiceImpl.getAllUsers())
                .build();
    }

    @GetMapping("/{userId}")
    private ApiResponse<UserResponse> getUserDetails(@PathVariable("userId") Integer userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.getUserDetails(userId))
                .build();
    }

    @GetMapping("/myInfo")
    private ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    private ApiResponse<UserResponse> updateUser(@PathVariable Integer userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userServiceImpl.updateUserById(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    private ApiResponse<String> deleteUser(@PathVariable Integer userId) {
        userServiceImpl.deleteUserById(userId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

}
