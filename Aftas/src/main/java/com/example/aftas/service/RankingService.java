package com.example.aftas.service;

import com.example.aftas.model.RankId;
import com.example.aftas.model.Ranking;

import java.util.List;

public interface RankingService {
    public Ranking registerMemberForCompetition(Ranking ranking);
    Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId);
    Ranking updateRanking(Ranking ranking, Long competitionId, Long memberId);
    void calculateAndSetRanks(Long competitionId);
}
