package com.example.trainbooking.entity;

import javax.persistence.Column;
import javax.persistence.Column;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
