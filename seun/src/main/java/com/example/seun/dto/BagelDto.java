package com.example.seun.dto;

import com.example.seun.entity.Bagel;
import lombok.Builder;
import lombok.Data;

@Data
public class BagelDto {
    private Long id;
    private String name;
    private Long price;

    @Builder
    public BagelDto(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Bagel toEntity(){
        return Bagel.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }
}
