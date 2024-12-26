package com.example.CodeDemoUntiTest.service;

import com.example.CodeDemoUntiTest.dto.request.UserUpdateRequest;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserDetails(Integer userId);
    UserResponse getMyInfo();
    UserResponse updateUserById(Integer userId, UserUpdateRequest request);
    void deleteUserById(Integer userId);

}
