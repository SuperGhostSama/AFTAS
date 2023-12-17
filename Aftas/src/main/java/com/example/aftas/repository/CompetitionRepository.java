package com.example.aftas.repository;

import com.example.aftas.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Competition findByDate(LocalDate date);
    List<Competition> findByEndTimeBefore(LocalTime endTime);

}
