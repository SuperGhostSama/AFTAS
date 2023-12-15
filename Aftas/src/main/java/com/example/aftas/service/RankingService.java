package com.example.aftas.service;

import com.example.aftas.model.Ranking;

import java.util.List;

public interface RankingService {
    public Ranking registerMemberForCompetition(Ranking ranking);
    Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId);
    Ranking getRankingById(Long id);
    Ranking updateRanking(Ranking ranking, Long id);
    Ranking updateRankingScore(Ranking ranking, Long id);
    void deleteRanking(Long id);
}
