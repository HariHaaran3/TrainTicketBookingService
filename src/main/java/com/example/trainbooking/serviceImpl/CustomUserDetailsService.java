package com.example.trainbooking.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.trainbooking.entity.UserDetail;
import com.example.trainbooking.repository.UserDetailRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("Loading user by username: " + userName);  // Add logging here
        Optional<UserDetail> userDetailOptional = userDetailRepository.findByUserName(userName);
        if (userDetailOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(userDetailOptional.get().getUserName(),
                                                                      userDetailOptional.get().getPassword(),
                                                                      new ArrayList<>());
    }
}
