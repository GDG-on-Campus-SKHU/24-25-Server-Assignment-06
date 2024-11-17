package com.example.jwtapplication.baemin.service;

import com.example.jwtapplication.baemin.domain.Restaurant;
import com.example.jwtapplication.baemin.dto.RestaurantInfoDto;
import com.example.jwtapplication.baemin.dto.RestaurantSaveRequestDto;
import com.example.jwtapplication.baemin.repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public RestaurantInfoDto createRestaurant(RestaurantSaveRequestDto restaurantSaveRequestDto) {
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantSaveRequestDto.getName())
                .master(restaurantSaveRequestDto.getMaster())
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
                return RestaurantInfoDto.from(savedRestaurant);

    }

    @Transactional(readOnly = true)
    public RestaurantInfoDto getRestaurant(Long id) { Restaurant restaurant = restaurantRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
            return RestaurantInfoDto.builder()
                    .id(restaurant.getId())
                    .name(restaurant.getName())
                    .master(restaurant.getMaster())
                    .build();
    }

    @Transactional
    public RestaurantInfoDto updateRestaurant(Long id, RestaurantInfoDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
         restaurant.update(restaurantDto);

        return RestaurantInfoDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .master(restaurant.getMaster())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RestaurantInfoDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantInfoDto> restaurantInfoDtos = restaurants.stream().map(RestaurantInfoDto::from).toList();

        return restaurantInfoDtos;


    }

}