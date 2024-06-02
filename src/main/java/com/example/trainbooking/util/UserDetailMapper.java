package com.example.trainbooking.util;

import java.util.stream.Collectors;

import com.example.trainbooking.entity.TicketBookingDetail;
import com.example.trainbooking.entity.UserDetail;
import com.example.trainbooking.model.TicketBookingDetailResponse;
import com.example.trainbooking.model.UserDetailRequest;
import com.example.trainbooking.model.UserDetailResponse;

public class UserDetailMapper {

    public static UserDetail toEntity(UserDetailRequest request) {
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(request.getUserId());
        userDetail.setUserName(request.getUserName());
        userDetail.setFirstName(request.getFirstName());
        userDetail.setLastName(request.getLastName());
        userDetail.setEmail(request.getEmail());
        userDetail.setPassword(request.getPassword());
        return userDetail;
    }

    public static UserDetailResponse toResponse(UserDetail userDetail) {
        UserDetailResponse response = new UserDetailResponse();
        response.setUserId(userDetail.getUserId());
        response.setUserName(userDetail.getUserName());
        response.setFirstName(userDetail.getFirstName());
        response.setLastName(userDetail.getLastName());
        response.setEmail(userDetail.getEmail());
        response.setPassword(userDetail.getPassword());
        response.setTicketBookingDetails(
            userDetail.getTicketBookingDetails()
                      .stream()
                      .map(UserDetailMapper::toResponse)
                      .collect(Collectors.toSet()));
        return response;
    }

    private static TicketBookingDetailResponse toResponse(TicketBookingDetail detail) {
        TicketBookingDetailResponse detailResponse = new TicketBookingDetailResponse();
        detailResponse.setTicketBookingId(detail.getTicketBookingId());
        detailResponse.setUserId(detail.getUserDetail().getUserId());
        detailResponse.setUserName(detail.getUserDetail().getUserName());
        detailResponse.setEmail(detail.getUserDetail().getEmail());
        detailResponse.setTrainNumber(detail.getTrainNumber());
        detailResponse.setTrainName(detail.getTrainName());
        detailResponse.setFromLocation(detail.getFromLocation());
        detailResponse.setToLocation(detail.getToLocation());
        detailResponse.setTicketCost(detail.getTicketCost());
        detailResponse.setSection(detail.getSection());
        detailResponse.setSeat(detail.getSeat());
        return detailResponse;
    }
}
