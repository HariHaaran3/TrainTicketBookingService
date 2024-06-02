package com.example.trainbooking.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainDetailRequest {
    private Long trainNumber;
    private String trainName;
    private String fromLocation;
    private String toLocation;
    private Set<TrainSectionAndSeatDetailRequest> trainSectionAndSeatDetails = new HashSet<>();
}
