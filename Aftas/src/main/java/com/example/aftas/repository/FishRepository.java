package com.example.aftas.repository;

import com.example.aftas.model.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishRepository extends JpaRepository<Fish, Long> {
    Fish findByName(String name);
}
