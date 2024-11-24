package com.example.tripplanner.repository;

import com.example.tripplanner.domain.Trip;
import com.example.tripplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(User user);
}
