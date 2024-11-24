package com.example.tripplanner.dto;

import com.example.tripplanner.domain.Trip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TripResponseDto {
    private Long tripId;
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;

    public static TripResponseDto from(Trip trip) {
        return TripResponseDto.builder()
                .tripId(trip.getTripId())
                .title(trip.getTitle())
                .destination(trip.getDestination())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .userId(trip.getTripId())
                .build();
    }
}
