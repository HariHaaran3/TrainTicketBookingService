package com.example.trainbooking.util;

import com.example.trainbooking.entity.TicketBookingDetail;
import com.example.trainbooking.model.TicketBookingDetailResponse;

public class TicketBookingDetailMapper {

    public static TicketBookingDetailResponse toResponse(TicketBookingDetail ticketBookingDetail) {
        TicketBookingDetailResponse response = new TicketBookingDetailResponse();
        response.setTicketBookingId(ticketBookingDetail.getTicketBookingId());
        response.setUserId(ticketBookingDetail.getUserDetail().getUserId());
        response.setUserName(ticketBookingDetail.getUserDetail().getUserName());
        response.setEmail(ticketBookingDetail.getUserDetail().getEmail());
        response.setTrainNumber(ticketBookingDetail.getTrainNumber());
        response.setTrainName(ticketBookingDetail.getTrainName());
        response.setFromLocation(ticketBookingDetail.getFromLocation());
        response.setToLocation(ticketBookingDetail.getToLocation());
        response.setTicketCost(ticketBookingDetail.getTicketCost());
        response.setSection(ticketBookingDetail.getSection());
        response.setSeat(ticketBookingDetail.getSeat());
        return response;
    }
}
