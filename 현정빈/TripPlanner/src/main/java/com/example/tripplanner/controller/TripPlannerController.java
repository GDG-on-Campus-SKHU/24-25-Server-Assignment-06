package com.example.tripplanner.controller;

import com.example.tripplanner.dto.TripListResponseDto;
import com.example.tripplanner.dto.TripRequestDto;
import com.example.tripplanner.dto.TripResponseDto;
import com.example.tripplanner.service.TripPlannerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TripPlannerController {
    private final TripPlannerService tripPlannerService;

    @PostMapping
    public ResponseEntity<TripResponseDto> saveTrip(@RequestBody TripRequestDto tripRequestDto){
        return new ResponseEntity<>(tripPlannerService.save(tripRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponseDto> findByTrip(@PathVariable Long tripId){
        return new ResponseEntity<>(tripPlannerService.findByTrip(tripId), HttpStatus.OK);
    }

    @PatchMapping("/{tripId}")
    public ResponseEntity<TripResponseDto> updateByTrip(@PathVariable Long tripId, @RequestBody TripRequestDto tripRequestDto){
        return new ResponseEntity<>(tripPlannerService.updateByTripId(tripId, tripRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<TripResponseDto> deleteByTripId(@PathVariable Long tripId){
        tripPlannerService.deleteByTripId(tripId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<TripListResponseDto> findByTripList(){
        return new ResponseEntity<>(tripPlannerService.findAllTrips(), HttpStatus.OK);
    }
}
