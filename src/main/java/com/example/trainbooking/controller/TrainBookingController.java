package com.example.trainbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.trainbooking.model.AuthenticationRequest;
import com.example.trainbooking.model.AuthenticationResponse;
import com.example.trainbooking.model.TicketBookingDetailRequest;
import com.example.trainbooking.model.TicketBookingDetailResponse;
import com.example.trainbooking.model.TrainDetailRequest;
import com.example.trainbooking.model.TrainDetailResponse;
import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;
import com.example.trainbooking.service.TrainBookingService;
import com.example.trainbooking.service.TrainDetailService;
import com.example.trainbooking.service.UserService;
import com.example.trainbooking.serviceImpl.CustomUserDetailsService;
import com.example.trainbooking.util.JwtUtil;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("trainTicketBooking/v1")
public class TrainBookingController {

    @Autowired
    private TrainBookingService trainBookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainDetailService trainDetailService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RateLimiter(name = "ticketBookingRateLimiter")
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @PostMapping("/trainDetail")
    public ResponseEntity<TrainDetailResponse> createTrainDetail(@RequestBody TrainDetailRequest trainDetail) {
        return ResponseEntity.ok(trainDetailService.createTrainDetail(trainDetail));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @PutMapping("/trainDetail/{trainNumber}")
    public ResponseEntity<TrainDetailResponse> updateTrainDetail(@PathVariable Long trainNumber,
                                                                 @RequestBody TrainDetailRequest trainDetail) {
        return ResponseEntity.ok(trainDetailService.updateTrainDetail(trainNumber, trainDetail));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @GetMapping("/trainDetail/{trainNumber}")
    public ResponseEntity<TrainDetailResponse> getTrainDetail(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(trainDetailService.getTrainDetail(trainNumber));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @DeleteMapping("/trainDetail/{trainNumber}")
    public ResponseEntity<Void> deleteTrainDetail(@PathVariable Long trainNumber) {
        trainDetailService.deleteTrainDetail(trainNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/purchaseTrainTicket")
    public ResponseEntity<TicketBookingDetailResponse> purchaseTrainTicket(@RequestBody TicketBookingDetailRequest ticketBookingDetailRequest) {
        return ResponseEntity.ok(trainBookingService.bookTicket(ticketBookingDetailRequest));
    }

    @GetMapping("/trainTicketReceipt/{ticketBookingId}")
    public ResponseEntity<TicketBookingDetailResponse> getTrainTicketReceiptByBookingId(@PathVariable Long ticketBookingId) {
        return ResponseEntity.ok(trainBookingService.getTicketDetails(ticketBookingId));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @PutMapping("/trainTicketReceipt/{ticketBookingId}/seat")
    public ResponseEntity<TicketBookingDetailResponse> updateSeat(@PathVariable Long ticketBookingId,
                                                                  @RequestParam String section,
                                                                  @RequestParam String newSeat) {
        return ResponseEntity.ok(trainBookingService.updateSeat(ticketBookingId, section, newSeat));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @DeleteMapping("/trainTicketReceipt/{ticketBookingId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketBookingId) {
        trainBookingService.deleteTicket(ticketBookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @GetMapping("/trainTicket/user/{userId}")
    public ResponseEntity<List<TicketBookingDetailResponse>> getUserTicketBookingDetails(@PathVariable Long userId) {
        return ResponseEntity.ok(trainBookingService.getUserTicketBookingDetails(userId));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @DeleteMapping("/trainTicket/user/{userId}")
    public ResponseEntity<Void> deleteUserTicketBookingDetails(@PathVariable Long userId) {
        trainBookingService.deleteUserTicketBookingDetails(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @GetMapping("/trainTicket/trainNumber/{trainNumber}/section/{section}")
    public ResponseEntity<List<TicketBookingDetailResponse>> getUserTicketBookingDetails(@PathVariable Long trainNumber,
                                                                                         @PathVariable String section) {
        return ResponseEntity.ok(trainBookingService.getAllottedTrainSectionDetails(trainNumber, section));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @PostMapping("/user")
    public ResponseEntity<UserDetailResponse> createUser(@RequestBody UserDetailRequest userDetailRequest) {
        return ResponseEntity.ok(userService.createUser(userDetailRequest));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetailResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @RateLimiter(name = "ticketBookingRateLimiter")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
