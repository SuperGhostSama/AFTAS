package com.example.aftas.repository;

import com.example.aftas.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Competition findByDate(LocalDate date);
    @Query("SELECT c FROM Competition c WHERE c.date >= CURRENT_DATE AND (c.date > CURRENT_DATE OR (c.date = CURRENT_DATE AND c.endTime > :endTime))")
    List<Competition> findUpcomingCompetitions(@Param("endTime") LocalTime endTime);
}


