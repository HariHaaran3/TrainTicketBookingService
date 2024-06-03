package com.example.trainbooking.serviceImpl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainbooking.entity.UserDetail;
import com.example.trainbooking.exception.BadRequestException;
import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;
import com.example.trainbooking.repository.UserDetailRepository;
import com.example.trainbooking.service.UserService;
import com.example.trainbooking.util.UserDetailMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    private Cache<Long, UserDetailResponse> userCache;

    public UserServiceImpl() {
        userCache = CacheBuilder.newBuilder()
                                .expireAfterWrite(10, TimeUnit.MINUTES)
                                .build();
    }

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
        UserDetailResponse userDetailResponse = UserDetailMapper.toResponse(savedUserDetail);
        userCache.put(savedUserDetail.getUserId(), userDetailResponse);
        return userDetailResponse;
    }

    @Override
    public UserDetailResponse getUser(Long userId) {
        UserDetailResponse cachedUserDetail = userCache.getIfPresent(userId);
        if (cachedUserDetail != null) {
            return cachedUserDetail;
        }

        Optional<UserDetail> userDetail = userDetailRepository.findById(userId);
        UserDetailResponse userDetailResponse = userDetail.map(UserDetailMapper::toResponse).orElse(null);
        if (null != userDetailResponse && null != userDetailResponse.getUserId()) {
            userCache.put(userId, userDetailResponse);
        }
        return userDetailResponse;
    }

    @Override
    public void deleteUser(Long userId) {
        userDetailRepository.deleteById(userId);
        userCache.invalidate(userId);
    }
}
