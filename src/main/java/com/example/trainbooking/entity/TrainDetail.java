package com.example.trainbooking.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
