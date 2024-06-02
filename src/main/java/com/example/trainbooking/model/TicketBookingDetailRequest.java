package com.example.trainbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketBookingDetailRequest {
    private Long userId;
    private Long trainNumber;
    private String trainName;
    private String fromLocation;
    private String toLocation;
    private double ticketCost;
    private Long sectionAndSeatId;
    private String section;
    private String seat;
}
