package com.example.seun.repository;

import com.example.seun.entity.Bagel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BagelRepository extends JpaRepository<Bagel, Long> {
    Optional<List<Bagel>> findByNameOrPrice(String name, Long price);
    Optional<List<Bagel>> findByName(String name);
    Optional<List<Bagel>> findByPrice(Long price);
}
