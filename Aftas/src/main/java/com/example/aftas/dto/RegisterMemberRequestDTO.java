package com.example.aftas.dto;

import com.example.aftas.model.Competition;
import com.example.aftas.model.Member;
import com.example.aftas.model.Ranking;

public record RegisterMemberRequestDTO(
        Long competitionId,
        Long memberId
) {
    public Ranking toRanking() {
        return Ranking.builder()
                .competition(Competition.builder().id(competitionId).build())
                .member(Member.builder().id(memberId).build())
                .build();
    }
}
