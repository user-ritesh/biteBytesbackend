package com.ritesh.biteBytes.food.dto;


import com.ritesh.biteBytes.food.entity.FoodEntity;

import java.util.List;

public class FoodListResponseDto {
    private boolean success;
    private List<FoodEntity> data;

    public FoodListResponseDto(boolean success, List<FoodEntity> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() { return success; }
    public List<FoodEntity> getData() { return data; }
}
