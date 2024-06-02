package com.example.trainbooking.service;

import com.example.trainbooking.model.TrainDetailRequest;
import com.example.trainbooking.model.TrainDetailResponse;

public interface TrainDetailService {

    TrainDetailResponse createTrainDetail(TrainDetailRequest trainDetail);

    TrainDetailResponse updateTrainDetail(Long trainNumber, TrainDetailRequest trainDetail);

    TrainDetailResponse getTrainDetail(Long trainNumber);

    void deleteTrainDetail(Long trainNumber);
}
