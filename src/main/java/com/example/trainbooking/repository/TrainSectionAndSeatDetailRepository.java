package com.example.trainbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trainbooking.entity.TrainSectionAndSeatDetail;

@Repository
public interface TrainSectionAndSeatDetailRepository extends JpaRepository<TrainSectionAndSeatDetail, Long> {

    Optional<TrainSectionAndSeatDetail> findByTrainDetailTrainNumberAndSection(Long trainNumber, String section);

    Optional<TrainSectionAndSeatDetail> findByTrainDetailTrainNumberAndSectionAndSeat(Long trainNumber, String section, String seat);
}