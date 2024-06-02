package com.example.trainbooking.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.trainbooking.entity.TrainDetail;
import com.example.trainbooking.entity.TrainSectionAndSeatDetail;
import com.example.trainbooking.exception.BadRequestException;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.model.TrainDetailRequest;
import com.example.trainbooking.model.TrainDetailResponse;
import com.example.trainbooking.model.TrainSectionAndSeatDetailRequest;
import com.example.trainbooking.repository.TrainDetailRepository;
import com.example.trainbooking.repository.TrainSectionAndSeatDetailRepository;

@ExtendWith(MockitoExtension.class)
public class TrainDetailServiceImplTest {

    @Mock
    private TrainDetailRepository trainDetailRepository;

    @Mock
    private TrainSectionAndSeatDetailRepository trainSectionAndSeatDetailRepository;

    @InjectMocks
    private TrainDetailServiceImpl trainDetailService;

    private TrainDetailRequest trainDetailRequest;
    private TrainDetail trainDetail;

    @BeforeEach
    public void setUp() {
        trainDetailRequest = new TrainDetailRequest();
        trainDetailRequest.setTrainName("Express");
        trainDetailRequest.setFromLocation("CityA");
        trainDetailRequest.setToLocation("CityB");

        TrainSectionAndSeatDetailRequest sectionAndSeatDetail = new TrainSectionAndSeatDetailRequest();
        sectionAndSeatDetail.setSection("A");
        sectionAndSeatDetail.setSeat("1");
        sectionAndSeatDetail.setTicketCost(100.0);

        Set<TrainSectionAndSeatDetailRequest> sectionAndSeatDetails = new HashSet<>();
        sectionAndSeatDetails.add(sectionAndSeatDetail);

        trainDetailRequest.setTrainSectionAndSeatDetails(sectionAndSeatDetails);

        trainDetail = new TrainDetail();
        trainDetail.setTrainNumber(1L);
        trainDetail.setTrainName("Express");
        trainDetail.setFromLocation("CityA");
        trainDetail.setToLocation("CityB");
        trainDetail.setTrainSectionAndSeatDetails(new HashSet<>());
    }

    @Test
    public void testCreateTrainDetail_TrainAlreadyExists() {
        when(trainDetailRepository.findByTrainDetails(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(trainDetail));

        assertThrows(BadRequestException.class, () -> {
            trainDetailService.createTrainDetail(trainDetailRequest);
        });
    }

    @Test
    public void testCreateTrainDetail_Success() {
        when(trainDetailRepository.findByTrainDetails(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(trainDetailRepository.save(any(TrainDetail.class))).thenReturn(trainDetail);

        TrainDetailResponse response = trainDetailService.createTrainDetail(trainDetailRequest);

        verify(trainDetailRepository).findByTrainDetails(anyString(), anyString(), anyString());
        verify(trainDetailRepository).save(any(TrainDetail.class));
        assertNotNull(response);
        assertEquals("Express", response.getTrainName());
    }

    @Test
    public void testUpdateTrainDetail_TrainNotFound() {
        when(trainDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            trainDetailService.updateTrainDetail(1L, trainDetailRequest);
        });
    }

    @Test
    @Transactional
    public void testUpdateTrainDetail_Success() {
        Set<TrainSectionAndSeatDetail> existingDetails = new HashSet<>();
        TrainSectionAndSeatDetail existingDetail = new TrainSectionAndSeatDetail();
        existingDetail.setSection("A");
        existingDetail.setSeat("1");
        existingDetail.setTicketCost(100.0);
        existingDetails.add(existingDetail);

        trainDetail.setTrainSectionAndSeatDetails(existingDetails);

        when(trainDetailRepository.findById(anyLong())).thenReturn(Optional.of(trainDetail));
        when(trainDetailRepository.save(any(TrainDetail.class))).thenReturn(trainDetail);

        TrainDetailResponse response = trainDetailService.updateTrainDetail(1L, trainDetailRequest);

        verify(trainDetailRepository).findById(anyLong());
        verify(trainSectionAndSeatDetailRepository).deleteAllInBatch(anySet());
        verify(trainSectionAndSeatDetailRepository, atLeastOnce()).save(any(TrainSectionAndSeatDetail.class));
        verify(trainDetailRepository).save(any(TrainDetail.class));

        assertNotNull(response);
        assertEquals("Express", response.getTrainName());
    }

    @Test
    public void testGetTrainDetail_TrainNotFound() {
        when(trainDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            trainDetailService.getTrainDetail(1L);
        });
    }

    @Test
    public void testGetTrainDetail_Success() {
        when(trainDetailRepository.findById(anyLong())).thenReturn(Optional.of(trainDetail));

        TrainDetailResponse response = trainDetailService.getTrainDetail(1L);

        verify(trainDetailRepository).findById(anyLong());
        assertNotNull(response);
        assertEquals("Express", response.getTrainName());
    }

    @Test
    public void testDeleteTrainDetail() {
        doNothing().when(trainDetailRepository).deleteById(anyLong());

        trainDetailService.deleteTrainDetail(1L);

        verify(trainDetailRepository).deleteById(anyLong());
    }
}
