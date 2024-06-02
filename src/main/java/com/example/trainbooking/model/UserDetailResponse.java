package com.example.trainbooking.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResponse {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<TicketBookingDetailResponse> ticketBookingDetails = new HashSet<>();
}
