package com.example.aftas.dto;

import com.example.aftas.model.Competition;
import com.example.aftas.model.Fish;
import com.example.aftas.model.Hunting;
import com.example.aftas.model.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HuntingRequestDTO(
        Long competitionId,
        Long memberId,
        Long fishId,

        @NotNull(message = "Weight is required")
        @Min(value = 1, message = "Weight must be greater than 0")
        double weight
) {
    public Hunting toHunting() {
        return Hunting.builder()
                .competition(Competition.builder().id(competitionId).build())
                .member(Member.builder().id(memberId).build())
                .fish(Fish.builder().id(fishId).weight(weight).build())
                .build();
    }
}
