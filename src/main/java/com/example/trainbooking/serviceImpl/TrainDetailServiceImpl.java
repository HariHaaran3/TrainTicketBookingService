package com.example.trainbooking.serviceImpl;

import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.trainbooking.entity.TrainDetail;
import com.example.trainbooking.entity.TrainSectionAndSeatDetail;
import com.example.trainbooking.exception.BadRequestException;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.model.TrainDetailRequest;
import com.example.trainbooking.model.TrainDetailResponse;
import com.example.trainbooking.repository.TrainDetailRepository;
import com.example.trainbooking.repository.TrainSectionAndSeatDetailRepository;
import com.example.trainbooking.service.TrainDetailService;
import com.example.trainbooking.util.TrainDetailMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class TrainDetailServiceImpl implements TrainDetailService {

    @Autowired
    private TrainDetailRepository trainDetailRepository;

    @Autowired
    private TrainSectionAndSeatDetailRepository trainSectionAndSeatDetailRepository;

    private Cache<Long, TrainDetailResponse> trainDetailCache;

    public TrainDetailServiceImpl() {
        trainDetailCache = CacheBuilder.newBuilder()
                                       .expireAfterWrite(10, TimeUnit.MINUTES)
                                       .build();
    }

    @Override
    public TrainDetailResponse createTrainDetail(TrainDetailRequest trainDetailRequest) {
        boolean trainExists = trainDetailRepository.findByTrainDetails(trainDetailRequest.getTrainName(),
                                                                       trainDetailRequest.getFromLocation(),
                                                                       trainDetailRequest.getToLocation()).isPresent();

        if (trainExists) {
            throw new BadRequestException("Train detail already exists with the provided details.");
        }

        TrainDetail trainDetail = TrainDetailMapper.toEntity(trainDetailRequest);
        TrainDetail savedTrainDetail = trainDetailRepository.save(trainDetail);
        TrainDetailResponse trainDetailResponse = TrainDetailMapper.toResponse(savedTrainDetail);
        trainDetailCache.put(savedTrainDetail.getTrainNumber(), trainDetailResponse);
        return trainDetailResponse;
    }

    @Override
    @Transactional
    public TrainDetailResponse updateTrainDetail(Long trainNumber, TrainDetailRequest trainDetailRequest) {
        TrainDetail trainDetail = trainDetailRepository.findById(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train Details Not Found"));

        updateTrainDetailFields(trainDetail, trainDetailRequest);
        updateTrainSectionAndSeatDetails(trainDetail, trainDetailRequest);

        TrainDetail savedTrainDetail = trainDetailRepository.save(trainDetail);
        TrainDetailResponse trainDetailResponse = TrainDetailMapper.toResponse(savedTrainDetail);
        trainDetailCache.put(savedTrainDetail.getTrainNumber(), trainDetailResponse);
        return trainDetailResponse;
    }

    private void updateTrainDetailFields(TrainDetail trainDetail, TrainDetailRequest trainDetailRequest) {
        trainDetail.setTrainName(trainDetailRequest.getTrainName());
        trainDetail.setFromLocation(trainDetailRequest.getFromLocation());
        trainDetail.setToLocation(trainDetailRequest.getToLocation());
    }

    private void updateTrainSectionAndSeatDetails(TrainDetail trainDetail, TrainDetailRequest trainDetailRequest) {
        trainSectionAndSeatDetailRepository.deleteAllInBatch(trainDetail.getTrainSectionAndSeatDetails());
        trainDetail.getTrainSectionAndSeatDetails().clear();

        trainDetailRequest.getTrainSectionAndSeatDetails().forEach(detailRequest -> {
            TrainSectionAndSeatDetail detail = new TrainSectionAndSeatDetail();
            detail.setTicketCost(detailRequest.getTicketCost());
            detail.setSection(detailRequest.getSection());
            detail.setSeat(detailRequest.getSeat());
            detail.setTrainDetail(trainDetail);

            trainSectionAndSeatDetailRepository.save(detail);
            trainDetail.getTrainSectionAndSeatDetails().add(detail);
        });
    }

    @Override
    public TrainDetailResponse getTrainDetail(Long trainNumber) {
        TrainDetailResponse cachedTrainDetail = trainDetailCache.getIfPresent(trainNumber);
        if (cachedTrainDetail != null) {
            return cachedTrainDetail;
        }

        TrainDetail trainDetail = trainDetailRepository.findById(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train Details Not Found"));

        TrainDetailResponse trainDetailResponse = TrainDetailMapper.toResponse(trainDetail);
        trainDetailCache.put(trainDetailResponse.getTrainNumber(), trainDetailResponse);
        return trainDetailResponse;
    }

    @Override
    public void deleteTrainDetail(Long trainNumber) {
        trainDetailRepository.deleteById(trainNumber);
        trainDetailCache.invalidate(trainNumber);
    }
}
