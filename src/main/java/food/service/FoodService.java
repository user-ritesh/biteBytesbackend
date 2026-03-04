package food.service;

import food.dto.FoodListResponseDto;
import food.dto.FoodResponseDto;
import food.dto.RemoveFoodRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {
    FoodListResponseDto listFood();
    FoodResponseDto addFood(String name, String description, Double price, String category, MultipartFile image);
    FoodResponseDto removeFood(RemoveFoodRequestDto request);
}
