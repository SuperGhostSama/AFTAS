package com.example.aftas.service.impl;

import com.example.aftas.config.handlers.exception.ResourceNotFoundException;
import com.example.aftas.model.*;
import com.example.aftas.repository.HuntingRepository;
import com.example.aftas.service.HuntingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HuntingServiceImpl implements HuntingService {
    private final HuntingRepository huntingRepository;
    private final FishServiceImpl fishService;
    private final MemberServiceImpl memberService;
    private final CompetitionServiceImpl competitionService;
    private final RankingServiceImpl rankingService;

    public HuntingServiceImpl(HuntingRepository huntingRepository, FishServiceImpl fishService, MemberServiceImpl memberService, CompetitionServiceImpl competitionService, RankingServiceImpl rankingService) {
        this.huntingRepository = huntingRepository;
        this.fishService = fishService;
        this.memberService = memberService;
        this.competitionService = competitionService;
        this.rankingService = rankingService;
    }

    @Override
    public Hunting getHuntingById(Long id) {
        return huntingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hunting id " + id + " not found"));
    }

    @Override
    public Hunting addHuntingResult(Hunting hunting) {
        Long competitionId = hunting.getCompetition().getId();
        Long memberId = hunting.getMember().getId();
        Long fishId = hunting.getFish().getId();

        // Check if competition exists
        Competition competition = competitionService.getCompetitionById(competitionId);

        // Check if the competition has ended
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime competitionEndTime = LocalDateTime.of(competition.getDate(), competition.getEndTime());

        if (currentDateTime.isAfter(competitionEndTime)) {
            throw new RuntimeException("The competition has ended. No more hunting results can be added.");
        }

        // Check if member exists
        Member member = memberService.getMemberById(memberId);

        // Check if fish exists
        Fish fish = fishService.getFishById(fishId);

        // Check if the member has already participated in this competition
        rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);

        // Check if fish has a level
        if (fish.getLevel() == null) {
            throw new ResourceNotFoundException("Fish id " + fishId + " has no level");
        }

        // Check if the weight of the fish is greater than the average weight
        if (hunting.getFish().getWeight() < fish.getWeight()) {
            throw new ResourceNotFoundException("Weight of fish must be greater than average weight");
        }

        // Check if the fish has already been caught by this member in this competition
        Hunting existingHunting = huntingRepository.findByCompetitionIdAndMemberIdAndFishId(competitionId, memberId, fishId);

        Ranking ranking = rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        ranking.setScore(ranking.getScore() + fish.getLevel().getPoint());
        rankingService.updateRanking(ranking, competitionId, memberId);

        if (existingHunting != null) {
            existingHunting.setNumberOfFish(existingHunting.getNumberOfFish() + 1);
            huntingRepository.save(existingHunting);
        } else {
            // Fish has not been caught, so add a new hunting record with numberOfFish set to 1
            hunting.setNumberOfFish(1);
            huntingRepository.save(hunting);
        }

        // Recalculate and set ranks after updating the scores
        rankingService.calculateAndSetRanks(competitionId);

        return hunting;
    }

    @Override
    public List<Hunting> getHuntingsByCompetition(Long competitionId) {
        return huntingRepository.findByCompetitionId(competitionId);
    }

    @Override
    public List<Hunting> getHuntingsByCompetitionAndMember(Long competitionId, Long memberId) {
        return huntingRepository.findByCompetitionIdAndMemberId(competitionId, memberId);
    }

    @Override
    public Hunting updateHunting(Hunting hunting, Long id) {
        Hunting existingHunting = getHuntingById(id);
        existingHunting.setFish(hunting.getFish());
        existingHunting.setMember(hunting.getMember());
        existingHunting.setCompetition(hunting.getCompetition());
        existingHunting.setNumberOfFish(hunting.getNumberOfFish());
        return huntingRepository.save(existingHunting);
    }

    @Override
    public void deleteHunting(Long id) {
        Hunting huntingToDelete = getHuntingById(id);
        Long competitionId = huntingToDelete.getCompetition().getId();
        Long memberId = huntingToDelete.getMember().getId();
        int numberOfFish = huntingToDelete.getNumberOfFish();

        // Check if competition has ended
        Competition competition = competitionService.getCompetitionById(competitionId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime competitionEndTime = LocalDateTime.of(competition.getDate(), competition.getEndTime());

        if (currentDateTime.isAfter(competitionEndTime)) {
            throw new RuntimeException("The competition has ended. No more hunting results can be deleted.");
        }

        // Check if member exists
        Member member = memberService.getMemberById(memberId);

        // Check if the member has already participated in this competition
        rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);

        // Check if the hunting record exists
        Hunting existingHunting = huntingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hunting id " + id + " not found"));

        // Check if the number of fish caught is greater than 0
        if (numberOfFish > 0) {
            // Reduce the score by the points associated with the fish's level
            Ranking ranking = rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
            int pointsToDeduct = numberOfFish * existingHunting.getFish().getLevel().getPoint();
            ranking.setScore(Math.max(0, ranking.getScore() - pointsToDeduct));
            rankingService.updateRanking(ranking, competitionId, memberId);
        }

        // Delete the hunting record
        huntingRepository.deleteById(id);

        // Recalculate and set ranks after updating the scores
        rankingService.calculateAndSetRanks(competitionId);
    }

}
