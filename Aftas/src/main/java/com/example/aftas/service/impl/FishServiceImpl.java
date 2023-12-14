package com.example.aftas.service.impl;

import com.example.aftas.config.handlers.exception.ResourceNotFoundException;
import com.example.aftas.model.Fish;
import com.example.aftas.repository.FishRepository;
import com.example.aftas.service.FishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishServiceImpl implements FishService {
    private final FishRepository fishRepository;
    private final LevelServiceImpl levelService;

    public FishServiceImpl(FishRepository fishRepository, LevelServiceImpl levelService) {
        this.fishRepository = fishRepository;
        this.levelService = levelService;
    }
    @Override
    public Fish getFishById(Long id) {
        return fishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fish id " + id + " not found"));
    }

    @Override
    public List<Fish> getAllFishes() {
        return fishRepository.findAll();
    }

    @Override
    public Fish addFish(Fish fish) {

        // Verify if fish name exists
        if(fishRepository.findByName(fish.getName()) != null) {
            throw new ResourceNotFoundException("Fish name " + fish.getName() + " already exist");
        }

        // Verify if level exists
        if(levelService.getLevelById(fish.getLevel().getId()) == null) {
            throw new ResourceNotFoundException("Level id " + fish.getLevel().getId() + " not found");
        }

        return fishRepository.save(fish);
    }

    @Override
    public Fish updateFish(Fish fish, Long id) {
        Fish existingFish = getFishById(id);
        existingFish.setName(fish.getName());
        existingFish.setWeight(fish.getWeight());

        if(levelService.getLevelById(fish.getLevel().getId()) == null) {
            throw new ResourceNotFoundException("Level id " + fish.getLevel().getId() + " not found");
        }
        existingFish.setLevel(fish.getLevel());
        return fishRepository.save(existingFish);
    }

    @Override
    public void deleteFish(Long id) {
        fishRepository.deleteById(id);
    }
}
