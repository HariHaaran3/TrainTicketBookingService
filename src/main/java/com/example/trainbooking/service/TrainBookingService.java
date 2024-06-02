package com.example.trainbooking.service;

import java.util.List;

import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.model.TicketBookingDetailRequest;
import com.example.trainbooking.model.TicketBookingDetailResponse;

public interface TrainBookingService {

    TicketBookingDetailResponse bookTicket(TicketBookingDetailRequest ticketBookingDetailRequest) throws ResourceNotFoundException;

    TicketBookingDetailResponse getTicketDetails(Long ticketBookingId);

    TicketBookingDetailResponse updateSeat(Long ticketBookingId, String section, String newSeat);

    void deleteTicket(Long ticketBookingId);

    List<TicketBookingDetailResponse> getUserTicketBookingDetails(Long userId);

    void deleteUserTicketBookingDetails(Long userId);

    List<TicketBookingDetailResponse> getAllottedTrainSectionDetails(Long trainNumber, String section);
}
