package com.example.aftas.service.impl;

import com.example.aftas.model.Competition;
import com.example.aftas.model.Member;
import com.example.aftas.model.RankId;
import com.example.aftas.model.Ranking;
import com.example.aftas.repository.RankingRepository;
import com.example.aftas.service.RankingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RankingServiceImpl implements RankingService {
    private final RankingRepository rankingRepository;
    private final MemberServiceImpl memberService;
    private final CompetitionServiceImpl competitionService;

    public RankingServiceImpl(RankingRepository rankingRepository, MemberServiceImpl memberService, CompetitionServiceImpl competitionService) {
        this.rankingRepository = rankingRepository;
        this.memberService = memberService;
        this.competitionService = competitionService;
    }

    @Override
    public Ranking registerMemberForCompetition(Ranking ranking) {

        Long competitionId = ranking.getCompetition().getId();

        Long memberId = ranking.getMember().getId();

//         Check if the competition exists
        Competition competition = competitionService.getCompetitionById(competitionId);

        // Check if the member exists
        Member member = memberService.getMemberById(memberId);

        // Check if the member is not already registered for the competition
        if (competition.getRanking().stream().anyMatch(r -> r.getMember().getId().equals(memberId))) {
            throw new RuntimeException("Member id " + memberId + " is already registered for the competition");
        }

        ranking.setId(new RankId(competition.getId(), member.getId()));
        return rankingRepository.save(ranking);
    }



    @Override
    public Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId) {
        memberService.getMemberById(memberId);
        competitionService.getCompetitionById(competitionId);
        Ranking ranking= rankingRepository.findByMemberIdAndCompetitionId(memberId, competitionId);
        if (ranking == null) {
            throw new RuntimeException("Member id " + memberId + " has not participated in competition id " + competitionId);
        }
        return ranking;
    }
    @Override
    public Ranking getRankingById(Long id) {
        return rankingRepository.findById(id).orElseThrow(() -> new RuntimeException("Ranking id " + id + " not found"));
    }
    @Override
    public Ranking updateRanking(Ranking ranking, Long id) {
        Ranking existingRanking = getRankingById(id);
        existingRanking.setRank(ranking.getRank());
        existingRanking.setScore(ranking.getScore());
        return rankingRepository.save(existingRanking);
    }

    @Override
    public Ranking updateRankingScore(Ranking ranking, Long id) {
        Ranking existingRanking = getRankingById(id);
        existingRanking.setScore(ranking.getScore()+existingRanking.getScore());
        return rankingRepository.save(existingRanking);
    }

    @Override
    public void deleteRanking(Long id) {
        getRankingById(id);
        rankingRepository.deleteById(id);
    }
}
