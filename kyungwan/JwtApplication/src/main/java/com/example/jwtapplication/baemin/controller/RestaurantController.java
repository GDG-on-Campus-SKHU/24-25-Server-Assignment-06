package com.example.jwtapplication.baemin.controller;

import com.example.jwtapplication.baemin.dto.RestaurantInfoDto;
import com.example.jwtapplication.baemin.dto.RestaurantSaveRequestDto;
import com.example.jwtapplication.baemin.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    //관리자일때만 음식점 추가 가능( 따로 매니저를 만들 수 있지만 어드민으로 통일 )
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantInfoDto> createRestaurant(@RequestBody RestaurantSaveRequestDto restaurantSaveRequestDto) {

        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantSaveRequestDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<RestaurantInfoDto>> getAllRestaurants(Principal principal) {
        List<RestaurantInfoDto> allRestaurants = restaurantService.getAllRestaurants();

        return ResponseEntity.ok(allRestaurants);
    }

}
