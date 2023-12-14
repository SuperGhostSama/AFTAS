package com.example.aftas.dto;

import com.example.aftas.model.Fish;
import com.example.aftas.model.Level;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

public record FishRequestDTO (
        @NotNull(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name must be alphanumeric")
        @Column(unique = true)
        String name,

        @NotNull(message = "Average weight is required")
        @Range(min = 0, max = 1000, message = "Weight must be between 0 and 1000")
        @Positive(message = "Weight must be greater than 0")
        double weight,
        Long level_id
){
    public Fish toFish() {
        Fish.FishBuilder fishBuilder =  new Fish().builder()
                .name(name)
                .weight(weight);
        if(level_id != null) {
            fishBuilder.level(Level.builder().id(level_id).build());
        }
        return fishBuilder.build();
    }
}
