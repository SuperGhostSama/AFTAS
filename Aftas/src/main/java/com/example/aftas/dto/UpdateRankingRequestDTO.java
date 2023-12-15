package com.example.aftas.dto;

import com.example.aftas.model.Ranking;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateRankingRequestDTO (

        @NotNull(message = "Score is required")
        @Min(value = 0, message = "Score must be greater than 0")
        int score
) {
    public Ranking toRanking() {
        return Ranking
                .builder()
                .score(score)
                .build();
    }
}
