package com.example.trainbooking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainSectionAndSeatDetailRequest {
    private Long sectionAndSeatId;
    private String section;
    private double ticketCost;
    private String seat;
    private TrainDetailRequest trainDetail;
}
