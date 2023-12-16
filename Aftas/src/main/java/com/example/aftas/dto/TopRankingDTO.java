package com.example.aftas.dto;

public record TopRankingDTO(
        int rank,
        int score,
        String memberName
) {
    public TopRankingDTO(int rank, int score, String memberName) {
        this.rank = rank;
        this.score = score;
        this.memberName = memberName;
    }
}
