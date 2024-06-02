package com.example.trainbooking.util;

import java.util.stream.Collectors;

import com.example.trainbooking.entity.TrainDetail;
import com.example.trainbooking.entity.TrainSectionAndSeatDetail;
import com.example.trainbooking.model.TrainDetailRequest;
import com.example.trainbooking.model.TrainDetailResponse;
import com.example.trainbooking.model.TrainSectionAndSeatDetailRequest;
import com.example.trainbooking.model.TrainSectionAndSeatDetailResponse;

public class TrainDetailMapper {

    public static TrainDetail toEntity(TrainDetailRequest trainDetailRequest) {
        TrainDetail trainDetail = new TrainDetail();
        trainDetail.setTrainName(trainDetailRequest.getTrainName());
        trainDetail.setFromLocation(trainDetailRequest.getFromLocation());
        trainDetail.setToLocation(trainDetailRequest.getToLocation());
        trainDetail.setTrainSectionAndSeatDetails(
            trainDetailRequest.getTrainSectionAndSeatDetails()
                              .stream()
                              .map(detailRequest -> toEntity(detailRequest, trainDetail))
                              .collect(Collectors.toSet())
        );
        return trainDetail;
    }

    public static TrainDetailResponse toResponse(TrainDetail trainDetail) {
        TrainDetailResponse trainDetailResponse = new TrainDetailResponse();
        trainDetailResponse.setTrainNumber(trainDetail.getTrainNumber());
        trainDetailResponse.setTrainName(trainDetail.getTrainName());
        trainDetailResponse.setFromLocation(trainDetail.getFromLocation());
        trainDetailResponse.setToLocation(trainDetail.getToLocation());
        trainDetailResponse.setTrainSectionAndSeatDetails(
            trainDetail.getTrainSectionAndSeatDetails()
                       .stream()
                       .map(TrainDetailMapper::toResponse)
                       .collect(Collectors.toSet())
        );
        return trainDetailResponse;
    }

    private static TrainSectionAndSeatDetail toEntity(TrainSectionAndSeatDetailRequest detailRequest, TrainDetail trainDetail) {
        TrainSectionAndSeatDetail detail = new TrainSectionAndSeatDetail();
        detail.setTicketCost(detailRequest.getTicketCost());
        detail.setSection(detailRequest.getSection());
        detail.setSeat(detailRequest.getSeat());
        detail.setTrainDetail(trainDetail);
        return detail;
    }

    private static TrainSectionAndSeatDetailResponse toResponse(TrainSectionAndSeatDetail detail) {
        TrainSectionAndSeatDetailResponse detailResponse = new TrainSectionAndSeatDetailResponse();
        detailResponse.setSectionAndSeatId(detail.getSectionAndSeatId());
        detailResponse.setTicketCost(detail.getTicketCost());
        detailResponse.setSection(detail.getSection());
        detailResponse.setSeat(detail.getSeat());
        return detailResponse;
    }
}
