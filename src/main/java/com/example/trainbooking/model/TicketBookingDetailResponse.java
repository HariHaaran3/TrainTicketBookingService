package com.example.trainbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketBookingDetailResponse {
    private Long ticketBookingId;
    private Long userId;
    private String userName;
    private String email;
    private Long trainNumber;
    private String trainName;
    private String fromLocation;
    private String toLocation;
    private double ticketCost;
    private String section;
    private String seat;
}
