package com.example.aftas.dto;

import com.example.aftas.model.Hunting;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HuntingUpdateRequestDTO(
        @NotNull(message = "Number of fish is required")
        @Min(value = 1, message = "Number of fish must be greater than 0")
        int numberOfFish
) {
    public Hunting toHunting() {
        return Hunting.builder()
                .numberOfFish(numberOfFish)
                .build();
    }
}
