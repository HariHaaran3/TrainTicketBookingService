package com.example.trainbooking.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainbooking.entity.UserDetail;
import com.example.trainbooking.exception.BadRequestException;
import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;
import com.example.trainbooking.repository.UserDetailRepository;
import com.example.trainbooking.service.UserService;
import com.example.trainbooking.util.UserDetailMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetailResponse createUser(UserDetailRequest userDetailRequest) {
        boolean userExists = userDetailRepository.findByUserDetails(userDetailRequest.getUserName(),
                                                                    userDetailRequest.getFirstName(),
                                                                    userDetailRequest.getLastName(),
                                                                    userDetailRequest.getEmail()).isPresent();

        if (userExists) {
            throw new BadRequestException("User detail already exists with the provided details.");
        }

        UserDetail userDetail = UserDetailMapper.toEntity(userDetailRequest);
        UserDetail savedUserDetail = userDetailRepository.save(userDetail);
        return UserDetailMapper.toResponse(savedUserDetail);
    }

    @Override
    public UserDetailResponse getUser(Long userId) {
        Optional<UserDetail> userDetail = userDetailRepository.findById(userId);
        return userDetail.map(UserDetailMapper::toResponse).orElse(null);
    }

    @Override
    public void deleteUser(Long userId) {
        userDetailRepository.deleteById(userId);
    }
}
