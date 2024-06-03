package com.example.trainbooking.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.trainbooking.entity.TicketBookingDetail;
import com.example.trainbooking.entity.TrainDetail;
import com.example.trainbooking.entity.TrainSectionAndSeatDetail;
import com.example.trainbooking.entity.UserDetail;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.model.TicketBookingDetailRequest;
import com.example.trainbooking.model.TicketBookingDetailResponse;
import com.example.trainbooking.repository.TicketBookingDetailRepository;
import com.example.trainbooking.repository.TrainDetailRepository;
import com.example.trainbooking.repository.TrainSectionAndSeatDetailRepository;
import com.example.trainbooking.repository.UserDetailRepository;
import com.google.common.cache.Cache;

@ExtendWith(MockitoExtension.class)
public class TrainBookingServiceImplTest {

    @InjectMocks
    private TrainBookingServiceImpl trainBookingService;

    @Mock
    private TicketBookingDetailRepository ticketBookingDetailRepository;

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private TrainDetailRepository trainDetailRepository;

    @Mock
    private TrainSectionAndSeatDetailRepository trainSectionAndSeatDetailRepository;

    @Mock
    private Cache<Long, TicketBookingDetailResponse> ticketBookingCache;

    private TicketBookingDetailRequest ticketBookingDetailRequest;
    private UserDetail userDetail;
    private TrainDetail trainDetail;
    private TrainSectionAndSeatDetail trainSectionAndSeatDetail;
    private TicketBookingDetail ticketBookingDetail;

    @BeforeEach
    void setUp() {
        ticketBookingDetailRequest = new TicketBookingDetailRequest();
        ticketBookingDetailRequest.setUserId(1L);
        ticketBookingDetailRequest.setTrainNumber(1L);
        ticketBookingDetailRequest.setSectionAndSeatId(1L);

        userDetail = new UserDetail();
        userDetail.setUserId(1L);

        trainDetail = new TrainDetail();
        trainDetail.setTrainNumber(1L);

        trainSectionAndSeatDetail = new TrainSectionAndSeatDetail();
        trainSectionAndSeatDetail.setSectionAndSeatId(1L);
        trainSectionAndSeatDetail.setSeatAllotted(false);

        ticketBookingDetail = new TicketBookingDetail();
        ticketBookingDetail.setUserDetail(userDetail);
        ticketBookingDetail.setTrainNumber(trainDetail.getTrainNumber());
        ticketBookingDetail.setSectionAndSeatId(trainSectionAndSeatDetail.getSectionAndSeatId());
    }

    @Test
    void testBookTicket_Success() {
        when(userDetailRepository.findById(anyLong())).thenReturn(Optional.of(userDetail));
        when(trainDetailRepository.findById(anyLong())).thenReturn(Optional.of(trainDetail));
        when(trainSectionAndSeatDetailRepository.findById(anyLong())).thenReturn(Optional.of(trainSectionAndSeatDetail));
        when(ticketBookingDetailRepository.save(any(TicketBookingDetail.class))).thenReturn(ticketBookingDetail);
        
        // This line may be causing unnecessary stubbing, comment it out
        // when(ticketBookingCache.getIfPresent(anyLong())).thenReturn(null);

        TicketBookingDetailResponse response = trainBookingService.bookTicket(ticketBookingDetailRequest);

        assertNotNull(response);
        verify(trainSectionAndSeatDetailRepository).save(any(TrainSectionAndSeatDetail.class));
        verify(ticketBookingDetailRepository).save(any(TicketBookingDetail.class));
    }

    @Test
    void testUpdateSeat_TrainSectionAndSeatNotFound() {
        when(ticketBookingDetailRepository.findById(anyLong())).thenReturn(Optional.of(ticketBookingDetail));
        when(trainSectionAndSeatDetailRepository.findByTrainDetailTrainNumberAndSectionAndSeat(anyLong(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            trainBookingService.updateSeat(1L, "A", "1")
        );
    }

    @Test
    void testUpdateSeat_SeatAlreadyAllotted() {
        when(ticketBookingDetailRepository.findById(anyLong())).thenReturn(Optional.of(ticketBookingDetail));
        trainSectionAndSeatDetail.setSeatAllotted(true);
        when(trainSectionAndSeatDetailRepository.findByTrainDetailTrainNumberAndSectionAndSeat(anyLong(), anyString(), anyString()))
                .thenReturn(Optional.of(trainSectionAndSeatDetail));

        assertThrows(ResourceNotFoundException.class, () -> 
            trainBookingService.updateSeat(1L, "A", "1")
        );
    }

    @Test
    void testUpdateSeat_Success() {
        when(ticketBookingDetailRepository.findById(anyLong())).thenReturn(Optional.of(ticketBookingDetail));
        when(trainSectionAndSeatDetailRepository.findByTrainDetailTrainNumberAndSectionAndSeat(anyLong(), anyString(), anyString()))
                .thenReturn(Optional.of(trainSectionAndSeatDetail));
        when(ticketBookingDetailRepository.save(any(TicketBookingDetail.class))).thenReturn(ticketBookingDetail);

        TicketBookingDetailResponse response = trainBookingService.updateSeat(1L, "A", "1");

        assertNotNull(response);
        verify(ticketBookingDetailRepository).save(any(TicketBookingDetail.class));
    }

    @Test
    void testGetTicketDetails_Success() {
        when(ticketBookingDetailRepository.findById(anyLong())).thenReturn(Optional.of(ticketBookingDetail));

        TicketBookingDetailResponse response = trainBookingService.getTicketDetails(1L);

        assertNotNull(response);
        verify(ticketBookingDetailRepository).findById(anyLong());
    }
}
