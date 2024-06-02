package com.example.trainbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.trainbooking.entity.TicketBookingDetail;

@Repository
public interface TicketBookingDetailRepository extends JpaRepository<TicketBookingDetail, Long> {

    List<TicketBookingDetail> findByUserDetailUserId(Long userId);

    List<TicketBookingDetail> findByTrainNumberAndSection(Long trainNumber, String section);
}
