package com.example.trainbooking.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.trainbooking.entity.UserDetail;
import com.example.trainbooking.exception.BadRequestException;
import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;
import com.example.trainbooking.repository.UserDetailRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDetailRepository userDetailRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDetailRequest userDetailRequest;
    private UserDetail userDetail;

    @BeforeEach
    public void setUp() {
        userDetailRequest = new UserDetailRequest();
        userDetailRequest.setUserName("testuser");
        userDetailRequest.setFirstName("Test");
        userDetailRequest.setLastName("User");
        userDetailRequest.setEmail("testuser@example.com");

        userDetail = new UserDetail();
        userDetail.setUserId(1L);
        userDetail.setUserName("testuser");
        userDetail.setFirstName("Test");
        userDetail.setLastName("User");
        userDetail.setEmail("testuser@example.com");
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        when(userDetailRepository.findByUserDetails(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(userDetail));

        assertThrows(BadRequestException.class, () -> {
            userService.createUser(userDetailRequest);
        });
    }

    @Test
    public void testCreateUser_Success() {
        when(userDetailRepository.findByUserDetails(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(userDetail);

        UserDetailResponse response = userService.createUser(userDetailRequest);

        verify(userDetailRepository).findByUserDetails(anyString(), anyString(), anyString(), anyString());
        verify(userDetailRepository).save(any(UserDetail.class));
        assertNotNull(response);
        assertEquals("testuser", response.getUserName());
    }

    @Test
    public void testGetUser_UserNotFound() {
        when(userDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserDetailResponse response = userService.getUser(1L);

        assertNull(response);
    }

    @Test
    public void testGetUser_Success() {
        when(userDetailRepository.findById(anyLong())).thenReturn(Optional.of(userDetail));

        UserDetailResponse response = userService.getUser(1L);

        assertNotNull(response);
        assertEquals("testuser", response.getUserName());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userDetailRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userDetailRepository).deleteById(anyLong());
    }
}
