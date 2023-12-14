package com.example.aftas.controller;

import com.example.aftas.dto.FishRequestDTO;
import com.example.aftas.config.handlers.response.ResponseMessage;
import com.example.aftas.model.Fish;
import com.example.aftas.service.FishService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fishes")
public class FishController {

    private FishService fishService;

    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping("{id}")
    public ResponseEntity getFishById(@PathVariable Long id) {
        Fish fish = fishService.getFishById(id);
        if(fish == null) {
            return ResponseMessage.notFound("Fish not found");
        }else {
            return ResponseMessage.ok(fish, "Success");
        }
    }

    @GetMapping
    public ResponseEntity getAllFishes() {
        List<Fish> fishes = fishService.getAllFishes();
        if(fishes.isEmpty()) {
            return ResponseMessage.notFound("Fish not found");
        }else {
            return ResponseMessage.ok(fishes, "Success");
        }
    }

    @PostMapping
    public ResponseEntity addFish(@Valid @RequestBody FishRequestDTO fishRequestDTO) {
        Fish fish = fishService.addFish(fishRequestDTO.toFish());
        if(fish == null) {
            return ResponseMessage.badRequest("Fish not created");
        }else {
            return ResponseMessage.created(fish, "Fish created successfully");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFish(@Valid @RequestBody FishRequestDTO fishRequestDTO, @PathVariable Long id) {
        Fish fish = fishService.updateFish(fishRequestDTO.toFish(), id);
        if(fish == null) {
            return ResponseMessage.badRequest("Fish not updated");
        }else {
            return ResponseMessage.created(fish, "Fish updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFish(@PathVariable Long id) {
        Fish fish = fishService.getFishById(id);
        if(fish == null) {
            return ResponseMessage.notFound("Fish not found");
        }else {
            fishService.deleteFish(id);
            return ResponseMessage.ok(null,"Fish deleted successfully");
        }
    }
}
