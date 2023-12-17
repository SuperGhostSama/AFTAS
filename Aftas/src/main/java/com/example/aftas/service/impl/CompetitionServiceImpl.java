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
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        // competition date is not in the past
        LocalDate currentDate = LocalDate.now();
        if (competition.getDate().isBefore(currentDate)) {
            throw new OperationException("Competition date cannot be in the past");
        }

        // Check if there is already a competition on the given date
        Competition existingCompetition = competitionRepository.findByDate(competition.getDate());
        if (existingCompetition != null) {
            throw new OperationException("There is already a competition on " + competition.getDate());
        }

        // Validate that the competition start time is before the end time
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }

        // Generate a unique code
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

    @Override
    public List<Competition> getCompetitionsByEndTime() {
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();

        // Combine the current date with the current time to create LocalDateTime
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);

        // Now extract the LocalTime from the LocalDateTime
        LocalTime currentLocalTime = currentDateTime.toLocalTime();

        return competitionRepository.findByEndTimeBefore(currentLocalTime);
    }

}
