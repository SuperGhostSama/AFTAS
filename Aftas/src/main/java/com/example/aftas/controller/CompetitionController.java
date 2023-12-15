package com.example.aftas.controller;

import com.example.aftas.dto.CompetitionRequestDTO;
import com.example.aftas.dto.RegisterMemberRequestDTO;
import com.example.aftas.config.handlers.response.ResponseMessage;
import com.example.aftas.model.Competition;
import com.example.aftas.model.Ranking;
import com.example.aftas.service.CompetitionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getCompetitionById(@PathVariable Long id) {
        Competition competition = competitionService.getCompetitionById(id);
        if(competition == null) {
            return ResponseMessage.notFound("Competition not found");
        }else {
            return ResponseMessage.ok(competition, "Success");
        }
    }

    @GetMapping
    public ResponseEntity getAllCompetitions() {
        List<Competition> competitions = competitionService.getAllCompetitions();
        if(competitions.isEmpty()) {
            return ResponseMessage.notFound("Competition not found");
        }else {
            return ResponseMessage.ok(competitions, "Success");
        }
    }

    @PostMapping
    public ResponseEntity addCompetition(@Valid @RequestBody CompetitionRequestDTO competitionRequestDTO) {
        Competition competition1 = competitionService.addCompetition(competitionRequestDTO.toCompetition());
        if(competition1 == null) {
            return ResponseMessage.badRequest("Competition not created");
        }else {
            return ResponseMessage.created(competition1, "Competition created successfully");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCompetition(@RequestBody CompetitionRequestDTO competitionRequestDTO, @PathVariable Long id) {
        System.out.println(competitionRequestDTO);
        Competition competition1 = competitionService.updateCompetition(competitionRequestDTO.toCompetition(), id);
        if(competition1 == null) {
            return ResponseMessage.badRequest("Competition not updated");
        }else {
            return ResponseMessage.created(competition1, "Competition updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
    }



}
