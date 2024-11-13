package com.example.seun.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Bagel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "BAGEL_NAME", nullable = false)
    private String name;

    @Column(name = "BAGEL_PRICE", nullable = false)
    private Long price;

    @Builder
    public Bagel(Long id, String name, Long price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
