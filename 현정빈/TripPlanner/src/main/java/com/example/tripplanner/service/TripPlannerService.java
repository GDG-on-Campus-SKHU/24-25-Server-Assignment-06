package com.example.tripplanner.service;

import com.example.tripplanner.domain.Trip;
import com.example.tripplanner.domain.User;
import com.example.tripplanner.dto.*;
import com.example.tripplanner.jwt.TokenProvider;
import com.example.tripplanner.repository.TripRepository;
import com.example.tripplanner.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TripPlannerService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Transactional
    public TripResponseDto save(TripRequestDto tripRequestDto) {
        User user = userRepository.findById(tripRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Trip trip = Trip.builder()
                .title(tripRequestDto.getTitle())
                .destination(tripRequestDto.getDestination())
                .startDate(tripRequestDto.getStartDate())
                .endDate(tripRequestDto.getEndDate())
                .user(user)
                .build();
        tripRepository.save(trip);
        return TripResponseDto.from(trip);
    }

    @Transactional(readOnly = true)
    public TripResponseDto findByTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행입니다"));
        return TripResponseDto.from(trip);

    }

    @Transactional
    public TripResponseDto updateByTripId(Long tripId, TripRequestDto tripRequestDto) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행입니다."));
        trip.update(tripRequestDto.getTitle(), tripRequestDto.getDestination(), tripRequestDto.getStartDate(), tripRequestDto.getEndDate());
        return TripResponseDto.from(trip);
    }

    @Transactional
    public void deleteByTripId(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    @Transactional
    public TripListResponseDto findAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        List<TripResponseDto> tripDtos = trips.stream()
                .map(TripResponseDto::from)
                .toList();
        return TripListResponseDto.from(tripDtos);
    }

}
