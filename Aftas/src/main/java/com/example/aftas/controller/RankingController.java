package com.example.aftas.controller;

import com.example.aftas.dto.UpdateRankingRequestDTO;
import com.example.aftas.config.handlers.response.ResponseMessage;
import com.example.aftas.model.Ranking;
import com.example.aftas.service.RankingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rankings")
public class RankingController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ResponseEntity getRankingById(@PathVariable Long id) {
        Ranking ranking = rankingService.getRankingById(id);
        return ResponseMessage.ok(ranking,"Success");
    }

    @GetMapping("/{competitionId}/{memberId}")
    public ResponseEntity getRankingsByMemberIdAndCompetitionId(@PathVariable Long competitionId, @PathVariable Long memberId) {
        Ranking ranking = rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        return ResponseMessage.ok(ranking,"Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRanking(@Valid @RequestBody UpdateRankingRequestDTO updateRankingRequestDTO, @PathVariable Long id) {
        Ranking ranking1 = rankingService.updateRanking(updateRankingRequestDTO.toRanking(), id);
        return ResponseMessage.ok(ranking1,"Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRanking(@PathVariable Long id) {
        rankingService.deleteRanking(id);
        return ResponseMessage.ok(null,"Ranking deleted successfully");
    }



}
