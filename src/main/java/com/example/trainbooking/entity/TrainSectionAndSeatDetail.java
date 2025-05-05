package com.example.trainbooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainSectionAndSeatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionAndSeatId;
    private String section;
    private double ticketCost;
    private String seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainNumber")
    @JsonBackReference
    private TrainDetail trainDetail;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isSeatAllotted = false;
}
