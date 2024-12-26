package com.example.CodeDemoUntiTest.mapper;

import com.example.CodeDemoUntiTest.dto.request.RegisterRequest;
import com.example.CodeDemoUntiTest.dto.request.UserUpdateRequest;
import com.example.CodeDemoUntiTest.dto.response.UserResponse;
import com.example.CodeDemoUntiTest.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registerUser(RegisterRequest request);
    UserResponse toUserResponse(User user);
    void updateUser (@MappingTarget User user, UserUpdateRequest request);
}
