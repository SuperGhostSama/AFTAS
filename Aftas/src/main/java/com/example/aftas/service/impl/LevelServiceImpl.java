package com.example.aftas.service.impl;

import com.example.aftas.config.handlers.exception.OperationException;
import com.example.aftas.config.handlers.exception.ResourceNotFoundException;
import com.example.aftas.model.Level;
import com.example.aftas.repository.LevelRepository;
import com.example.aftas.service.LevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }
    @Override
    public Level getLevelById(Long id) {
        return levelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Level id " + id + " not found"));
    }

    @Override
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @Override
    public Level addLevel(Level level) {
        Level levels = levelRepository.findAll().stream().max((l1, l2) -> l1.getPoint() > l2.getPoint() ? 1 : -1).orElse(null);
        if(levels != null) {
            if(level.getPoint() <= levels.getPoint()) {
                throw new OperationException("Point must be greater than " + levels.getPoint());
            }
        }
        return levelRepository.save(level);
    }

    @Override
    public Level updateLevel(Level level, Long id) {
        Level existingLevel = getLevelById(id);
        existingLevel.setDescription(level.getDescription());
        // check if point is greater than previous level and less than next level
        List<Level> allLevels = levelRepository.findAll();
        int index = id.intValue()-1;
        if(index==0){
            if(level.getPoint() >= allLevels.get((index+1)).getPoint()) {
                throw new OperationException("Point must be less than " + allLevels.get((index+1)).getPoint());
            }
        }else if(index==allLevels.size()-1){
            if(level.getPoint() <= allLevels.get((index-1)).getPoint()) {
                throw new OperationException("Point must be greater than " + allLevels.get((index-1)).getPoint());
            }
        }else{
            if(level.getPoint() >= allLevels.get((index+1)).getPoint()) {
                throw new OperationException("Point must be less than " + allLevels.get((index+1)).getPoint());
            }
            if(level.getPoint() <= allLevels.get((index-1)).getPoint()) {
                throw new OperationException("Point must be greater than " + allLevels.get((index-1)).getPoint());
            }
        }
        existingLevel.setPoint(level.getPoint());
        return levelRepository.save(existingLevel);
    }

    @Override
    public void deleteLevel(Long id) {
        levelRepository.deleteById(id);
    }
}
