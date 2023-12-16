package com.example.aftas.repository;

import com.example.aftas.model.RankId;
import com.example.aftas.model.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, RankId> {
    Ranking findByMemberIdAndCompetitionId(Long memberId, Long competitionId);


    @Query("SELECT r FROM Ranking r WHERE r.competition.id = :competitionId ORDER BY r.score DESC")
    List<Ranking> findAllByCompetitionIdOrderByScoreDesc(Long competitionId);

    @Query("SELECT COUNT(DISTINCT r.member.id) FROM Ranking r WHERE r.id.competitionId = :competitionId")
    int countDistinctByCompetitionId(@Param("competitionId") Long competitionId);
}
