package com.example.trainbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainSectionAndSeatDetailResponse {
    private Long sectionAndSeatId;
    private String section;
    private double ticketCost;
    private String seat;
}
