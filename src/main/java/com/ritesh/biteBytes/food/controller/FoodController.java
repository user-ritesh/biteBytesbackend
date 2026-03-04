package com.ritesh.biteBytes.food.controller;

import com.ritesh.biteBytes.food.dto.FoodListResponseDto;
import com.ritesh.biteBytes.food.dto.FoodResponseDto;
import com.ritesh.biteBytes.food.dto.RemoveFoodRequestDto;
import com.ritesh.biteBytes.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/list")
    public ResponseEntity<FoodListResponseDto> listFood() {
        return ResponseEntity.ok(foodService.listFood());
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<FoodResponseDto> addFood(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) {

        return ResponseEntity.ok(foodService.addFood(name, description, price, category, image));
    }

    @PostMapping("/remove")
    public ResponseEntity<FoodResponseDto> removeFood(@RequestBody RemoveFoodRequestDto request) {
        return ResponseEntity.ok(foodService.removeFood(request));
    }
}