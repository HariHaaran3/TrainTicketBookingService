package com.example.trainbooking.service;

import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;

public interface UserService {

    UserDetailResponse createUser(UserDetailRequest userDetailRequest);

    UserDetailResponse getUser(Long userId);

    void deleteUser(Long userId);
}
