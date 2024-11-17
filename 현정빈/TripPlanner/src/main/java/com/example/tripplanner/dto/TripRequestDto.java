package com.example.tripplanner.dto;

import com.example.tripplanner.domain.Trip;
import com.example.tripplanner.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TripRequestDto {
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;

    public Trip toEntity(User user) {
        return Trip.builder()
                .title(title)
                .destination(destination)
                .startDate(startDate)
                .endDate(endDate)
                .user(user)
                .build();
    }
}
