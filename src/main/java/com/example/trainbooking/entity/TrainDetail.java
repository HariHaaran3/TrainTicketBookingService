package com.example.trainbooking.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainNumber;
    private String trainName;
    private String fromLocation;
    private String toLocation;

    @OneToMany(mappedBy = "trainDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<TrainSectionAndSeatDetail> trainSectionAndSeatDetails = new HashSet<>();
}
