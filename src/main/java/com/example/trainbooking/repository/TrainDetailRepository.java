package com.example.trainbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.trainbooking.entity.TrainDetail;

@Repository
public interface TrainDetailRepository extends JpaRepository<TrainDetail, Long> {

    @Query("SELECT td FROM TrainDetail td WHERE td.trainName = :trainName AND td.fromLocation = :fromLocation AND td.toLocation = :toLocation")
    Optional<TrainDetail> findByTrainDetails(@Param("trainName") String trainName,
                                             @Param("fromLocation") String fromLocation,
                                             @Param("toLocation") String toLocation);
}