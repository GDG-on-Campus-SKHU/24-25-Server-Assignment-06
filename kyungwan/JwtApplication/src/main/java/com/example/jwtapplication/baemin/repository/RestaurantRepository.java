package com.example.jwtapplication.baemin.repository;


import com.example.jwtapplication.baemin.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
