package com.example.aftas.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.aftas.dto.HuntingRequestDTO;
import com.example.aftas.model.*;
import com.example.aftas.repository.HuntingRepository;
import com.example.aftas.service.CompetitionService;
import com.example.aftas.service.FishService;
import com.example.aftas.service.MemberService;
import com.example.aftas.service.RankingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HuntingServiceImplTest {

    @Mock
    private CompetitionService competitionService;

    @Mock
    private MemberService memberService;

    @Mock
    private FishService fishService;

    @Mock
    private RankingService rankingService;

    @Mock
    private HuntingRepository huntingRepository;

    @InjectMocks
    private HuntingServiceImpl huntingService;

    @Test
    void testAddHuntingResult_Successful() {

        Competition competition = new Competition();
        competition.setId(1L);

        Member member = new Member();
        member.setId(2L);

        Fish fish = new Fish();
        fish.setId(3L);

        Hunting hunting = new Hunting();
        hunting.setCompetition(competition);
        hunting.setMember(member);
        hunting.setFish(fish);

        // Mock the behavior of the dependencies
        when(memberService.getMemberById(2L)).thenReturn(member);
        when(fishService.getFishById(3L)).thenReturn(fish);
        when(rankingService.getRankingsByMemberIdAndCompetitionId(1L, 2L)).thenReturn(new Ranking());
        when(huntingRepository.findByCompetitionIdAndMemberIdAndFishId(1L, 2L, 3L)).thenReturn(null);
        when(rankingService.getRankingsByMemberIdAndCompetitionId(1L, 2L)).thenReturn(new Ranking());

        // Call the method
        Hunting result = huntingService.addHuntingResult(hunting);
        assertNotNull(result);

    }

}
