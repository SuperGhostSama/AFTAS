package com.example.aftas.service.impl;

import com.example.aftas.config.handlers.exception.OperationException;
import com.example.aftas.config.handlers.exception.ResourceNotFoundException;
import com.example.aftas.model.Competition;
import com.example.aftas.model.Member;
import com.example.aftas.model.Ranking;
import com.example.aftas.repository.CompetitionRepository;
import com.example.aftas.repository.RankingRepository;
import com.example.aftas.service.CompetitionService;
import com.example.aftas.service.MemberService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }
    @Override
    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Competition id " + id + " not found"));
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public List<Competition> getCompetitionsByStatus(String status) {
        return null;
    }

    @Override
    public Competition addCompetition(Competition competition) {

        // here i want to check that Every day there can be only one competition
        Competition competition1 = competitionRepository.findByDate(competition.getDate());
        if (competition1 != null) {
            throw new OperationException("There is already a competition on " + competition.getDate());
        }

        // here i want to check that Competition start time must be before end time
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }

        // here i want to generate a unique code for the competition from that date and location  pattern: ims-22-12-23, ims is the third first letters of the location
        String code = generateCode(competition.getLocation(), competition.getDate());
        competition.setCode(code);

        return competitionRepository.save(competition);

    }

    public static String generateCode(String location, LocalDate date) {
        String locationCode = location.substring(0, 3).toLowerCase();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        String formattedDate = date.format(dateFormatter);

        return locationCode + "-" + formattedDate;
    }

    @Override
    public Competition updateCompetition(Competition competition, Long id) {
        Competition existingCompetition = getCompetitionById(id);
        // check if date is changed so we can check if there is already a competition on that date
        System.out.println(competition.getDate());
        System.out.println(existingCompetition.getDate());
        if (!competition.getDate().equals(existingCompetition.getDate())) {
            Competition competition1 = competitionRepository.findByDate(competition.getDate());
            if (competition1 != null) {
                throw new OperationException("There is already a competition on " + competition.getDate());
            }
        }
        existingCompetition.setDate(competition.getDate());
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }
        existingCompetition.setStartTime(competition.getStartTime());
        existingCompetition.setEndTime(competition.getEndTime());
        existingCompetition.setLocation(competition.getLocation());
        if(competition.getLocation() != existingCompetition.getLocation() || competition.getDate() != existingCompetition.getDate()){
            String code = generateCode(competition.getLocation(), competition.getDate());
            existingCompetition.setCode(code);
        }

        existingCompetition.setAmount(competition.getAmount());
        return competitionRepository.save(existingCompetition);
    }

    @Override
    public void deleteCompetition(Long id) {
        getCompetitionById(id);
        competitionRepository.deleteById(id);
    }

}
