package com.example.aftas.service;

import com.example.aftas.model.Competition;
import com.example.aftas.model.Ranking;

import java.util.List;

public interface CompetitionService {
    Competition getCompetitionById(Long id);
    List<Competition> getAllCompetitions();
    List<Competition> getCompetitionsByStatus(String status);
    Competition addCompetition(Competition competition);
    Competition updateCompetition(Competition competition, Long id);
    void deleteCompetition(Long id);
}
