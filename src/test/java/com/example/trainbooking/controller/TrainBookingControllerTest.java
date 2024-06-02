package com.example.trainbooking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.trainbooking.model.TicketBookingDetailRequest;
import com.example.trainbooking.model.TicketBookingDetailResponse;
import com.example.trainbooking.model.TrainDetailRequest;
import com.example.trainbooking.model.TrainDetailResponse;
import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;
import com.example.trainbooking.service.TrainBookingService;
import com.example.trainbooking.service.TrainDetailService;
import com.example.trainbooking.service.UserService;

@ExtendWith(MockitoExtension.class)
public class TrainBookingControllerTest {

    @InjectMocks
    private TrainBookingController trainBookingController;

    @Mock
    private TrainBookingService trainBookingService;

    @Mock
    private UserService userService;

    @Mock
    private TrainDetailService trainDetailService;

    private TrainDetailRequest trainDetailRequest;
    private TrainDetailResponse trainDetailResponse;
    private TicketBookingDetailRequest ticketBookingDetailRequest;
    private TicketBookingDetailResponse ticketBookingDetailResponse;
    private UserDetailRequest userDetailRequest;
    private UserDetailResponse userDetailResponse;

    @BeforeEach
    void setUp() {
        trainDetailRequest = new TrainDetailRequest();
        trainDetailRequest.setTrainName("Express");
        trainDetailRequest.setFromLocation("CityA");
        trainDetailRequest.setToLocation("CityB");

        trainDetailResponse = new TrainDetailResponse();
        trainDetailResponse.setTrainName("Express");

        ticketBookingDetailRequest = new TicketBookingDetailRequest();
        ticketBookingDetailRequest.setUserId(1L);
        ticketBookingDetailRequest.setTrainNumber(1L);
        ticketBookingDetailRequest.setSectionAndSeatId(1L);

        ticketBookingDetailResponse = new TicketBookingDetailResponse();
        ticketBookingDetailResponse.setTrainNumber(1L);

        userDetailRequest = new UserDetailRequest();
        userDetailRequest.setUserName("testuser");
        userDetailRequest.setFirstName("Test");
        userDetailRequest.setLastName("User");
        userDetailRequest.setEmail("testuser@example.com");

        userDetailResponse = new UserDetailResponse();
        userDetailResponse.setUserName("testuser");
    }

    @Test
    void testCreateTrainDetail_Success() {
        when(trainDetailService.createTrainDetail(any(TrainDetailRequest.class))).thenReturn(trainDetailResponse);

        ResponseEntity<TrainDetailResponse> response = trainBookingController.createTrainDetail(trainDetailRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainDetailService).createTrainDetail(any(TrainDetailRequest.class));
    }

    @Test
    void testUpdateTrainDetail_Success() {
        when(trainDetailService.updateTrainDetail(anyLong(), any(TrainDetailRequest.class))).thenReturn(trainDetailResponse);

        ResponseEntity<TrainDetailResponse> response = trainBookingController.updateTrainDetail(1L, trainDetailRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainDetailService).updateTrainDetail(anyLong(), any(TrainDetailRequest.class));
    }

    @Test
    void testGetTrainDetail_Success() {
        when(trainDetailService.getTrainDetail(anyLong())).thenReturn(trainDetailResponse);

        ResponseEntity<TrainDetailResponse> response = trainBookingController.getTrainDetail(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainDetailService).getTrainDetail(anyLong());
    }

    @Test
    void testDeleteTrainDetail_Success() {
        ResponseEntity<Void> response = trainBookingController.deleteTrainDetail(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(trainDetailService).deleteTrainDetail(anyLong());
    }

    @Test
    void testPurchaseTrainTicket_Success() {
        when(trainBookingService.bookTicket(any(TicketBookingDetailRequest.class))).thenReturn(ticketBookingDetailResponse);

        ResponseEntity<TicketBookingDetailResponse> response = trainBookingController.purchaseTrainTicket(ticketBookingDetailRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainBookingService).bookTicket(any(TicketBookingDetailRequest.class));
    }

    @Test
    void testGetTrainTicketReceiptByBookingId_Success() {
        when(trainBookingService.getTicketDetails(anyLong())).thenReturn(ticketBookingDetailResponse);

        ResponseEntity<TicketBookingDetailResponse> response = trainBookingController.getTrainTicketReceiptByBookingId(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainBookingService).getTicketDetails(anyLong());
    }

    @Test
    void testUpdateSeat_Success() {
        when(trainBookingService.updateSeat(anyLong(), anyString(), anyString())).thenReturn(ticketBookingDetailResponse);

        ResponseEntity<TicketBookingDetailResponse> response = trainBookingController.updateSeat(1L, "A", "1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(trainBookingService).updateSeat(anyLong(), anyString(), anyString());
    }

    @Test
    void testDeleteTicket_Success() {
        ResponseEntity<Void> response = trainBookingController.deleteTicket(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(trainBookingService).deleteTicket(anyLong());
    }

    @Test
    void testCreateUser_Success() {
        when(userService.createUser(any(UserDetailRequest.class))).thenReturn(userDetailResponse);

        ResponseEntity<UserDetailResponse> response = trainBookingController.createUser(userDetailRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).createUser(any(UserDetailRequest.class));
    }

    @Test
    void testGetUser_Success() {
        when(userService.getUser(anyLong())).thenReturn(userDetailResponse);

        ResponseEntity<UserDetailResponse> response = trainBookingController.getUser(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).getUser(anyLong());
    }

    @Test
    void testDeleteUser_Success() {
        ResponseEntity<Void> response = trainBookingController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).deleteUser(anyLong());
    }
}
