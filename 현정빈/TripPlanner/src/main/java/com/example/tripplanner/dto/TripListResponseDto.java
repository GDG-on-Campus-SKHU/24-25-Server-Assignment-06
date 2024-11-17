package com.example.tripplanner.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class TripListResponseDto {
    List<TripResponseDto> trips;

    public static TripListResponseDto from(List<TripResponseDto> trips) {
        return TripListResponseDto.builder()
                .trips(trips)
                .build();
    }
}
