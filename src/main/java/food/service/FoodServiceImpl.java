package food.service;


import food.dto.FoodListResponseDto;
import food.dto.FoodResponseDto;
import food.dto.RemoveFoodRequestDto;
import food.entity.FoodEntity;
import food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public FoodListResponseDto listFood() {
        try {
            List<FoodEntity> foods = foodRepository.findAll();
            return new FoodListResponseDto(true, foods);
        } catch (Exception e) {
            e.printStackTrace();
            return new FoodListResponseDto(false, null);
        }
    }

    @Override
    public FoodResponseDto addFood(String name, String description, Double price, String category, MultipartFile image) {
        try {
            // 1. Save the image
            String imageFilename = fileStorageService.saveFile(image);

            // 2. Map parameters to Entity
            FoodEntity food = new FoodEntity();
            food.setName(name);
            food.setDescription(description);
            food.setPrice(price);
            food.setCategory(category);
            food.setImage(imageFilename);

            // 3. Save to database
            foodRepository.save(food);

            return new FoodResponseDto(true, "Food Added");
        } catch (Exception e) {
            e.printStackTrace();
            return new FoodResponseDto(false, "Error");
        }
    }

    @Override
    public FoodResponseDto removeFood(RemoveFoodRequestDto request) {
        try {
            Optional<FoodEntity> foodOpt = foodRepository.findById(request.getId());

            if (foodOpt.isPresent()) {
                FoodEntity food = foodOpt.get();

                // 1. Delete the image file
                fileStorageService.deleteFile(food.getImage());

                // 2. Delete the database record
                foodRepository.deleteById(request.getId());

                return new FoodResponseDto(true, "Food Removed");
            }
            return new FoodResponseDto(false, "Food not found");
        } catch (Exception e) {
            e.printStackTrace();
            return new FoodResponseDto(false, "Error");
        }
    }
}

