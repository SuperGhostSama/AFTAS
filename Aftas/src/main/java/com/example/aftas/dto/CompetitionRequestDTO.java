package com.example.aftas.dto;

import com.example.aftas.model.Competition;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public record CompetitionRequestDTO(
        @NotNull(message = "Name cannot be null")
        @Temporal(TemporalType.DATE)
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "Start time cannot be null")
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime startTime,

        @NotNull(message = "End time cannot be null")
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime endTime,

        @NotNull(message = "Location cannot be null")
        @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters")
        String location,

        @NotNull(message = "Amount cannot be null")
        @Min(value = 0, message = "Amount must be greater than 0")
        int amount,

        @NotNull(message = "Total member cannot be null")
        @Min(value = 0, message = "Total member must be greater than 0")
        int numberOfParticipants

) {
    public Competition toCompetition() {
        return Competition.builder()
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .location(location)
                .amount(amount)
                .numberOfParticipants(numberOfParticipants)
                .build();
    }
}
