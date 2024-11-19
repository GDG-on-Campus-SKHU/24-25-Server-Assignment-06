package com.example.seun.service;

import com.example.seun.dto.BagelDto;
import com.example.seun.entity.Bagel;
import com.example.seun.repository.BagelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BagelService {
    private final BagelRepository bagelRepository;

    public BagelService(BagelRepository bagelRepository) {
        this.bagelRepository = bagelRepository;
    }

    public void addBagel(BagelDto bageldto) {
        Bagel bagel = bageldto.toEntity();
        bagelRepository.save(bagel);
    }

    public Bagel getBagelById(long id) {
        return bagelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("베이글을 찾을 수 없습니다 : " + id));
    }

    public List<Bagel> getAllDesserts() {
        return bagelRepository.findAll();
    }

    public List<Bagel> searchDesserts(String name, Long price) {
        return bagelRepository.findByNameOrPrice(name, price)
                .orElseGet(Collections::emptyList);
    }

    public long countBagels() {
        return bagelRepository.count();
    }

    @Transactional
    public void updateDessert(Long id, String name, Long price) {
        if (id == null) {
            throw new IllegalArgumentException("ID는 null일 수 없습니다.");
        }

        Bagel bagel = bagelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ID " + id + "의 디저트를 찾을 수 없습니다."));

        if (name != null) bagel.setName(name);
        if (price != null) bagel.setPrice(price);

        bagelRepository.save(bagel);
    }

    @Transactional
    public void removeBagelById(Long id) {
        Bagel bagel = bagelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "의 디저트가 존재하지 않습니다."));

        bagelRepository.delete(bagel);
    }

    @Transactional
    public void removeAllDesserts() {
        bagelRepository.deleteAll();
    }

}
