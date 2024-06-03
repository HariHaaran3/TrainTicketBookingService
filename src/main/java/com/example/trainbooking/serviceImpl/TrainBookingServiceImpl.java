package com.example.trainbooking.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.example.trainbooking.service.TrainBookingService;
import com.example.trainbooking.util.TicketBookingDetailMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainBookingServiceImpl implements TrainBookingService {

    @Autowired
    private TicketBookingDetailRepository ticketBookingDetailRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private TrainDetailRepository trainDetailRepository;

    @Autowired
    private TrainSectionAndSeatDetailRepository trainSectionAndSeatDetailRepository;

    private Cache<Long, TicketBookingDetailResponse> ticketBookingCache;

    public TrainBookingServiceImpl() {
        ticketBookingCache = CacheBuilder.newBuilder()
                                         .expireAfterWrite(10, TimeUnit.MINUTES)
                                         .build();
    }

    @Override
    @Transactional
    public TicketBookingDetailResponse bookTicket(TicketBookingDetailRequest ticketBookingDetailRequest) {
        UserDetail userDetail = userDetailRepository.findById(ticketBookingDetailRequest.getUserId())
                .orElseThrow(() -> {
                    log.info("User Details Not Found");
                    return new ResourceNotFoundException("User Details Not Found");
                });

        TrainDetail trainDetail = trainDetailRepository.findById(ticketBookingDetailRequest.getTrainNumber())
                .orElseThrow(() -> {
                    log.info("Train Details Not Found");
                    return new ResourceNotFoundException("Train Details Not Found");
                });

        TrainSectionAndSeatDetail trainSectionAndSeatDetail = trainSectionAndSeatDetailRepository
                .findById(ticketBookingDetailRequest.getSectionAndSeatId()).orElseThrow(() -> {
                    log.info("Train Section And Seat Details Not Found");
                    return new ResourceNotFoundException("Train Section And Seat Details Not Found");
                });

        if (trainSectionAndSeatDetail.isSeatAllotted()) {
            log.info("Train Seat Already Allotted");
            throw new ResourceNotFoundException("Train Seat Already Allotted");
        }

        TicketBookingDetail ticketBookingDetail = new TicketBookingDetail();
        ticketBookingDetail.setUserDetail(userDetail);
        ticketBookingDetail.setTrainNumber(trainDetail.getTrainNumber());
        ticketBookingDetail.setTrainName(trainDetail.getTrainName());
        ticketBookingDetail.setFromLocation(trainDetail.getFromLocation());
        ticketBookingDetail.setToLocation(trainDetail.getToLocation());
        ticketBookingDetail.setSectionAndSeatId(trainSectionAndSeatDetail.getSectionAndSeatId());
        ticketBookingDetail.setTicketCost(trainSectionAndSeatDetail.getTicketCost());
        ticketBookingDetail.setSection(trainSectionAndSeatDetail.getSection());
        ticketBookingDetail.setSeat(trainSectionAndSeatDetail.getSeat());

        trainSectionAndSeatDetail.setSeatAllotted(true);
        trainSectionAndSeatDetailRepository.save(trainSectionAndSeatDetail);

        ticketBookingDetail = ticketBookingDetailRepository.save(ticketBookingDetail);
        TicketBookingDetailResponse response = TicketBookingDetailMapper.toResponse(ticketBookingDetail);
        ticketBookingCache.put(ticketBookingDetail.getTicketBookingId(), response);
        return response;
    }

    @Override
    public TicketBookingDetailResponse getTicketDetails(Long ticketBookingId) {
        TicketBookingDetailResponse cachedTicketDetail = ticketBookingCache.getIfPresent(ticketBookingId);
        if (cachedTicketDetail != null) {
            return cachedTicketDetail;
        }

        TicketBookingDetail ticketBookingDetail = ticketBookingDetailRepository.findById(ticketBookingId)
                .orElseThrow(() -> {
                    log.info("Ticket Booking Detail Not Found");
                    return new ResourceNotFoundException("Ticket Booking Detail Not Found");
                });

        TicketBookingDetailResponse response = TicketBookingDetailMapper.toResponse(ticketBookingDetail);
        ticketBookingCache.put(response.getTicketBookingId(), response);
        return response;
    }

    @Override
    public TicketBookingDetailResponse updateSeat(Long ticketBookingId, String section, String newSeat) {
        TicketBookingDetail bookingDetail = ticketBookingDetailRepository.findById(ticketBookingId).orElseThrow(() -> {
            log.info("Ticket Booking Detail Not Found");
            return new ResourceNotFoundException("Ticket Booking Detail Not Found");
        });

        TrainSectionAndSeatDetail trainSectionAndSeatDetail = trainSectionAndSeatDetailRepository
                .findByTrainDetailTrainNumberAndSectionAndSeat(bookingDetail.getTrainNumber(), section, newSeat)
                .orElseThrow(() -> {
                    log.info("Train Section And Seat Details Not Found");
                    return new ResourceNotFoundException("Train Section And Seat Details Not Found");
                });

        if (trainSectionAndSeatDetail.isSeatAllotted()) {
            log.info("Train Seat Already Allotted");
            throw new ResourceNotFoundException("Train Seat Already Allotted");
        }

        bookingDetail.setSeat(trainSectionAndSeatDetail.getSeat());
        ticketBookingDetailRepository.save(bookingDetail);

        TicketBookingDetailResponse response = TicketBookingDetailMapper.toResponse(bookingDetail);
        ticketBookingCache.put(response.getTicketBookingId(), response);
        return response;
    }

    @Override
    public void deleteTicket(Long ticketBookingId) {
        Optional<TicketBookingDetail> ticketBookingOptional = ticketBookingDetailRepository.findById(ticketBookingId);
        if (ticketBookingOptional.isPresent()) {
            TicketBookingDetail ticketBooking = ticketBookingOptional.get();
            Optional<TrainSectionAndSeatDetail> trainSectionAndSeatDetailOptional = trainSectionAndSeatDetailRepository
                    .findById(ticketBooking.getSectionAndSeatId());
            if (trainSectionAndSeatDetailOptional.isPresent()) {
                TrainSectionAndSeatDetail trainSectionAndSeatDetail = trainSectionAndSeatDetailOptional.get();
                trainSectionAndSeatDetail.setSeatAllotted(false);
                trainSectionAndSeatDetailRepository.save(trainSectionAndSeatDetail);
            } else {
                throw new ResourceNotFoundException(
                        "Train section and seat detail not found for ID: " + ticketBooking.getSectionAndSeatId());
            }
            ticketBookingDetailRepository.deleteById(ticketBookingId);
            ticketBookingCache.invalidate(ticketBookingId);
        } else {
            throw new ResourceNotFoundException("Ticket booking not found for ID: " + ticketBookingId);
        }}

    @Override
    public List<TicketBookingDetailResponse> getUserTicketBookingDetails(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> {
            log.info("User Details Not Found");
            return new ResourceNotFoundException("User Details Not Found");
        });

        List<TicketBookingDetail> ticketBookingDetails = ticketBookingDetailRepository
                .findByUserDetailUserId(userDetail.getUserId());

        List<TicketBookingDetailResponse> responses = new ArrayList<>();
        ticketBookingDetails.forEach(detail -> responses.add(TicketBookingDetailMapper.toResponse(detail)));
        return responses;
    }

    @Override
    public void deleteUserTicketBookingDetails(Long userId) {
        UserDetail userDetail = userDetailRepository.findById(userId).orElseThrow(() -> {
            log.info("User Details Not Found");
            return new ResourceNotFoundException("User Details Not Found");
        });

        List<TicketBookingDetail> ticketBookingDetails = ticketBookingDetailRepository
                .findByUserDetailUserId(userDetail.getUserId());

        for (TicketBookingDetail ticketBooking : ticketBookingDetails) {
            Optional<TrainSectionAndSeatDetail> trainSectionAndSeatDetailOptional = trainSectionAndSeatDetailRepository
                    .findById(ticketBooking.getSectionAndSeatId());
            if (trainSectionAndSeatDetailOptional.isPresent()) {
                TrainSectionAndSeatDetail trainSectionAndSeatDetail = trainSectionAndSeatDetailOptional.get();
                trainSectionAndSeatDetail.setSeatAllotted(false);
                trainSectionAndSeatDetailRepository.save(trainSectionAndSeatDetail);
            } else {
                log.warn("Train section and seat detail not found for ID: " + ticketBooking.getSectionAndSeatId());
                throw new ResourceNotFoundException(
                        "Train section and seat detail not found for ID: " + ticketBooking.getSectionAndSeatId());
            }
            ticketBookingDetailRepository.deleteById(ticketBooking.getTicketBookingId());
            ticketBookingCache.invalidate(ticketBooking.getTicketBookingId());
        }
    }

    @Override
    public List<TicketBookingDetailResponse> getAllottedTrainSectionDetails(Long trainNumber, String section) {
        TrainDetail trainDetail = trainDetailRepository.findById(trainNumber).orElseThrow(() -> {
            log.info("Train Details Not Found");
            return new ResourceNotFoundException("Train Details Not Found");
        });

        boolean sectionExists = trainDetail.getTrainSectionAndSeatDetails().stream()
                .anyMatch(sectionAndSeat -> sectionAndSeat.getSection().equalsIgnoreCase(section));

        if (!sectionExists) {
            log.info("Section Not Found in Train Details");
            throw new ResourceNotFoundException("Section Not Found in Train Details");
        }

        List<TicketBookingDetail> ticketBookingDetails = ticketBookingDetailRepository
                .findByTrainNumberAndSection(trainNumber, section);

        if (ticketBookingDetails.isEmpty()) {
            log.info("No Ticket Booking Details Found for Train Number: " + trainNumber + " and Section: " + section);
            throw new ResourceNotFoundException(
                    "No Ticket Booking Details Found for Train Number: " + trainNumber + " and Section: " + section);
        }

        // Map the ticket booking details to responses
        List<TicketBookingDetailResponse> responses = ticketBookingDetails.stream()
                .map(TicketBookingDetailMapper::toResponse).collect(Collectors.toList());

        return responses;
    }
}
