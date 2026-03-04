package com.ritesh.biteBytes.food.service;

import com.ritesh.biteBytes.food.dto.FoodListResponseDto;
import com.ritesh.biteBytes.food.dto.FoodResponseDto;
import com.ritesh.biteBytes.food.dto.RemoveFoodRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {
    FoodListResponseDto listFood();
    FoodResponseDto addFood(String name, String description, Double price, String category, MultipartFile image);
    FoodResponseDto removeFood(RemoveFoodRequestDto request);
}
