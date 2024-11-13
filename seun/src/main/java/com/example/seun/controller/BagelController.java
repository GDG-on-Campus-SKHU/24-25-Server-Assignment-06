package com.example.seun.controller;

import com.example.seun.dto.BagelDto;
import com.example.seun.entity.Bagel;
import com.example.seun.service.BagelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bagels")
public class BagelController {

    private final BagelService bagelService;

    public BagelController(BagelService bagelService) {
        this.bagelService = bagelService;
    }

    @PostMapping
    public ResponseEntity<Void> addBagel(@RequestBody BagelDto bagelDto) {
        bagelService.addBagel(bagelDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bagel> getBagelById(@PathVariable Long id) {
        Bagel bagel = bagelService.getBagelById(id);
        return ResponseEntity.ok(bagel);
    }

    @GetMapping
    public ResponseEntity<List<Bagel>> getAllBagels() {
        List<Bagel> bagel = bagelService.getAllDesserts();
        return ResponseEntity.ok(bagel);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Bagel>> searchBagels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long price) {
        List<Bagel> bagel = bagelService.searchDesserts(name, price);
        return ResponseEntity.ok(bagel);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> countBagels() {
        long count = bagelService.countBagels();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bagel> updateDessert(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long price) {
        bagelService.updateDessert(id, name, price);
        Bagel bagel = bagelService.getBagelById(id);
        return ResponseEntity.ok(bagel);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeAllBagels() {
        bagelService.removeAllDesserts();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBagelById(@PathVariable Long id) {
        bagelService.removeBagelById(id);
        return ResponseEntity.noContent().build();
    }

}
